package loader;

/**
 * -XX:+TraceClassLoading 查看类是否加载
 */

/**
 * Java中对类的使用分为两种
 *  1.主动使用：会执行加载、连接、初始化静态域
 *  2、被动加载：只执行加载、连接、不出适合静态域
 */

class A{
    public static int a =10;
    static {
        System.out.println("A.a="+a);
    }
}

class B extends A{
    static {
        System.out.println("B");
    }
}

public class TestClassLoader1 {
    public static void main(String[] args) {
        System.out.println(B.a);//不会执行B类中的静态代码块
    }
}
