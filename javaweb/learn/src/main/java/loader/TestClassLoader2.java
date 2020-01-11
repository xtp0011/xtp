package loader;

import java.util.ArrayList;

/**
 * 类加载器
 */
public class TestClassLoader2 {
    public static void main(String[] args) {
        doMetode1();
        System.out.println("------------------------------");
        doMetode2();
        System.out.println("------------------------------");
        doMetode3();
    }

    public static void doMetode3() {
        System.out.println("ClassLoader of this class: "
                +TestClassLoader2.class.getClassLoader());//AppClassLoader
        System.out.println("ClassLoader of ArrayList: "
                + ArrayList.class.getClassLoader());//null BootStrapClassLoader
    }

    public static void doMetode1(){
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        System.out.println(loader);//AppClassLoader
        System.out.println(loader.getParent());//ExClassLoader
        System.out.println(loader.getParent().getParent());//null BootStrapClassLoader
    }

    public static void doMetode2(){
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.out.println(loader);
        System.out.println(loader.getParent());
        System.out.println(loader.getParent().getParent());
    }
}
