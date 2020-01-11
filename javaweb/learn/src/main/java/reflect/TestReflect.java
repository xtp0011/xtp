package reflect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Comtponent{
    String value() default "";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface DI{}


@Comtponent("config")
class Configuration<T>{
    private String path;
    public Configuration(){}
    public Configuration(String path){
        this.path=path;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "path='" + path + '\'' +
                '}';
    }

    public String getPath() {
        return path;
    }

    @DI
    private void setPath(String path) {
        this.path = path;
    }
}

public class TestReflect {
    public static void main(String[] args) throws Exception{
        Class<?> cls = Class.forName("reflect.Configuration");
        Class<?> type = cls.getComponentType();
        System.out.println(type);
    }

    private static void doInstance4() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        Class<?> cls = Class.forName("reflect.Configuration");
        Constructor<?> con = cls.getDeclaredConstructor();
        con.setAccessible(true);
        Object o = con.newInstance();
        //Method method = cls.getMethod("setPath",String.class);
        Method method = cls.getDeclaredMethod("setPath", String.class);
        method.setAccessible(true);
        boolean flag = method.isAnnotationPresent(DI.class);
        if(flag) method.invoke(o,"classpath:cfg.xml");
        System.out.println(o);
    }

    private static void doIntance3() throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        Class<?> cls = Class.forName("reflect.Configuration");
        Field f1 = cls.getDeclaredField("path");
        Constructor<?> con = cls.getDeclaredConstructor();
        con.setAccessible(true);
        Object o = con.newInstance();
        f1.setAccessible(true);
        f1.set(o,"classpath:cfg.xml");
        System.out.println(o);
    }

    private static void doIntanes2() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        Class<?> cls = Class.forName("reflect.Configuration");
        Constructor<?> con = cls.getDeclaredConstructor();
        boolean flag = cls.isAnnotationPresent(Comtponent.class);
        if(!flag) return;
        con.setAccessible(true);
        Object o = con.newInstance();
        Map<String,Object> map = new HashMap<>();
        Comtponent comtponent = cls.getAnnotation(Comtponent.class);
        String value = comtponent.value();
        map.put(value,o);
        System.out.println(map);
    }

    private static void doInstance1() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        Class<?> cls = Class.forName("reflect.Configuration");
        Constructor<?> con = cls.getDeclaredConstructor();
        con.setAccessible(true);
        Object o = con.newInstance();
        System.out.println(o);
    }

}
