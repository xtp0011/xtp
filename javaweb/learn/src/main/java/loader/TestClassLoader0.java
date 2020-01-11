package loader;

class ClassB{
    static int cap =10;
    static {
        System.out.println("ClassB.static");
    }
    static void doPrint(){
        System.out.println("print class info");
    }
}

class SubClassB extends ClassB{
    static {
        System.out.println("SubClassB.static");
    }
}

/**
 * 隐身加载
 */
public class TestClassLoader0 {
    public static void main(String[] args) {
       // System.out.println(ClassB.cap);
       // ClassB.doPrint();
       // new ClassB();
        new SubClassB();
    }
}
