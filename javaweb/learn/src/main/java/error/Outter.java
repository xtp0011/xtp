package error;

public class Outter {
    static class Inner{
        static int one = 5;
        static final int two = 5;
        static final int three = new Integer(5);
    }

    public static void main(String[] args) {
        System.out.println(Inner.one);
        System.out.println(Inner.two);
        System.out.println(Inner.three);
    }

}
