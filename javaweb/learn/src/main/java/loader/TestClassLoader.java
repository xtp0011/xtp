package loader;

/**
 * 显示加载
 */

// -XX:+TraceClassLoading 查看类是否加载
class ClassA{
    static {
        System.out.println("--------ClassA---------");
    }
}

public class TestClassLoader {
    public static void main(String[] args) throws ClassNotFoundException {
        /**
         * 显示加载
         */
        //方式一：不初始化
        ClassLoader loader = TestClassLoader.class.getClassLoader();
        //Class<?> aClass = loader.loadClass("loader.ClassA");

        //方式二 ：初始化
        //Class.forName("loader.ClassA");


        //true 初始化 , false 不初始化
        Class.forName("loader.ClassA",false,loader);

    }
}
