package com.xtp.sourceanalysis.spring.spring.factory;

import com.xtp.sourceanalysis.spring.spring.Service.SysLogService;
import com.xtp.sourceanalysis.spring.spring.Service.SysUserService;
import com.xtp.sourceanalysis.spring.spring.annotation.ComponentScan;
import com.xtp.sourceanalysis.spring.spring.annotation.Lazy;
import com.xtp.sourceanalysis.spring.spring.annotation.Service;
import com.xtp.sourceanalysis.spring.spring.config.SpringConfig;
import com.xtp.sourceanalysis.spring.spring.vo.BeanDefinition;

import java.awt.*;
import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AnnotationConfigApplictionContext {

    //存储bean的实例
    private Map<String,Object> instanceMpa = new HashMap<>();

    //存储配置文件中定义的bean对象的配置信息
    private Map<String,BeanDefinition> beanMap = new HashMap<>();


    public AnnotationConfigApplictionContext(Class<?> configClass)throws Exception {
        //1.读取配置类中指定的包名
        ComponentScan scan = configClass.getDeclaredAnnotation(ComponentScan.class);
        String pkg = scan.value();
        //2.扫描指定的类路径
        String classPath = pkg.replace(".", "/");
        URL url = configClass.getClassLoader().getSystemResource(classPath);
       // System.out.println(url);
        File fileDir = new File(url.getPath());
        File[] classFile = fileDir.listFiles(
                file -> file.isFile()&&file.getName().endsWith(".class"));

        /*File[] classFile = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile()&&pathname.getName().endsWith(".class");
            }
        });*/
        //3.封装文件信息
        processClassFile(pkg,classFile);
    }

    private void processClassFile(String pkg,File[] classFile) throws Exception{
        for (File file:classFile) {
            String pkgClass = pkg+"."+file.getName().substring(0,file.getName().lastIndexOf("."));
            Class<?> targetClass = Class.forName(pkgClass);
            Service service = targetClass.getDeclaredAnnotation(Service.class);
            if(service==null) continue;
            BeanDefinition bean = new BeanDefinition();
            bean.setId(service.value());
            bean.setPkgClass(pkgClass);
            Lazy lazy = targetClass.getDeclaredAnnotation(Lazy.class);
            if(lazy!=null){
                bean.setLazy(lazy.value());
            }
           beanMap.put(bean.getId(),bean);
           if(!bean.getLazy()){
              Object object = newBeanInstance(targetClass);
              instanceMpa.put(bean.getId(),object);
           }
        }
    }

    private Object newBeanInstance(Class<?> targetClass) throws Exception{
        Constructor<?> constructor = targetClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }


    public <T> T getBean(String key,Class<T> cls)throws Exception{
        Object object = instanceMpa.get(key);
        if(object!=null) return (T) object;
        object = newBeanInstance(cls);
        instanceMpa.put(key,object);
        return (T) object;
    }

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplictionContext ctx =
                new AnnotationConfigApplictionContext(SpringConfig.class);
        System.out.println(ctx.instanceMpa);
        SysLogService sysLogService = ctx.getBean("sysLogService", SysLogService.class);
        SysUserService sysUserService = ctx.getBean("sysUserService", SysUserService.class);
        System.out.println(sysLogService);
        System.out.println(sysUserService);
    }
}
