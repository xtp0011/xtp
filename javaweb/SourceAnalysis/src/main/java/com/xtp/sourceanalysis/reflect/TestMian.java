package com.xtp.sourceanalysis.reflect;

import org.springframework.util.StringUtils;

import java.lang.annotation.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface Component{
    String value() default "";
}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface DI{
}

@Component
class Configuration{
    private String path;
    public Configuration(String path) {
        this.path=path;
    }

    public Configuration() {

    }

    public String getPath() {
        return path;
    }

    @DI
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "path='" + path + '\'' +
                '}';
    }
}

public class TestMian {
    public static void main(String[] args) throws  Exception{
        //do1();
        //do2();
        //do3();
        //do4();
        //do5();
        //do6();
        //do7();

        Class<?> cls = Class.forName("com.xtp.sourceanalysis.reflect.Configuration");
        Constructor<?> constructor = cls.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object instance = constructor.newInstance();
        Method method = cls.getDeclaredMethod("setPath", String.class);
        if(method.isAnnotationPresent(DI.class)){
            method.invoke(instance,"classpath:cfg.xml");
        }
        System.out.println(instance);

    }

    private static void do7() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException, NoSuchFieldException {
        Class<?> cls = Class.forName("com.xtp.sourceanalysis.reflect.Configuration");
        Constructor<?> constructor = cls.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object instance = constructor.newInstance();
        Field path = cls.getDeclaredField("path");
        path.setAccessible(true);
        path.set(instance,"classpath:cfg.xml");
        Method method = cls.getDeclaredMethod("getPath");
        Object invoke = method.invoke(instance);
        System.out.println(invoke);
    }

    private static void do6() throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        Class<?> cls = Class.forName("com.xtp.sourceanalysis.reflect.Configuration");
        Field path = cls.getDeclaredField("path");
        System.out.println(path);
        Constructor<?> constructor = cls.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object instance = constructor.newInstance();
        path.setAccessible(true);
        path.set(instance,"classpath:cfg.xml");
        System.out.println(instance);
    }

    private static void do5() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        Class<?> cls = Class.forName("com.xtp.sourceanalysis.reflect.Configuration");
        boolean flag = cls.isAnnotationPresent(Component.class);
        if(!flag)return;
        Constructor<?> constructor = cls.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object instance = constructor.newInstance();
        Map<String,Object> map = new HashMap<>();
        Component annotation = cls.getAnnotation(Component.class);
        String beanName = annotation.value();
        if(StringUtils.isEmpty(beanName)){
            beanName=cls.getSimpleName().toLowerCase();
        }
        map.put(beanName,instance);
        System.out.println(map);
    }

    private static void do4() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        Class<?> cls = Class.forName("com.xtp.sourceanalysis.reflect.Configuration");
        boolean flag = cls.isAnnotationPresent(Component.class);
        if(!flag)return;
        Constructor<?> constructor = cls.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object instance = constructor.newInstance();
        System.out.println(instance);
    }

    private static void do3() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        Class<?> cls = Class.forName("com.xtp.sourceanalysis.reflect.Configuration");
        Constructor<?> constructor = cls.getDeclaredConstructor(String.class);
        constructor.setAccessible(true);
        Object instance = constructor.newInstance("www.baidu.com");
        System.out.println(instance);
    }

    private static void do2() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        Class<?> cls = Class.forName("com.xtp.sourceanalysis.reflect.Configuration");
        Constructor<?> constructor = cls.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object instance = constructor.newInstance();
        System.out.println(instance);
    }

    private static void do1() throws ClassNotFoundException {
        Configuration con = new Configuration();
        Class<?> cls1 = con.getClass();
        Class<?> cls2 = Configuration.class;
        Class<?> cls3 = Class.forName("com.xtp.sourceanalysis.reflect.Configuration");
        System.out.println(cls1==cls2);
        System.out.println(cls3==cls2);
    }
}
