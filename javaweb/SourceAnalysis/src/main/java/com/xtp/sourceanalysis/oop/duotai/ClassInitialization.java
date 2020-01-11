package com.xtp.sourceanalysis.oop.duotai;

import java.util.Random;

/**

 加载：由类加载器执行。查找字节码，并从这些字节码中创建一个Class对象 -

 链接：验证类中的字节码，为静态域分配存储空间，并且如果必需的话，
 将解析这个类创建的对其他类的所有引用。 -

 初始化：如果该类具有超类，则对其初始化，执行静态初始化器和静态初始化块。
 *
 *
 */

class Initable{
    static final int staticFinal = 47;
    static final int staticFinal2 = ClassInitialization.rand.nextInt(1000);

    static {
        System.out.println("Initializing Initable");
    }
}

class Initable2 {
    static int staticNonFinal = 147;

    static {
        System.out.println("Initializing Initable2");
    }
}

class Initable3 {
    static int staticNonFinal = 74;

    static {
        System.out.println("Initializing Initable3");
    }
}



public class ClassInitialization {
    public static Random rand = new Random(47);

    public static void main(String[] args){
        // Does not trigger initialization
        Class initable = Initable.class;
        System.out.println("After creating Initable ref");
        // Does not trigger initialization
        System.out.println(Initable.staticFinal);
        // Does trigger initialization(rand() is static method)
        System.out.println(Initable.staticFinal2);

        // Does trigger initialization(not final)
        System.out.println(Initable2.staticNonFinal);

        try {
            Class initable3 = Class.forName("com.xtp.sourceanalysis.oop.duotai.Initable3");
        } catch (ClassNotFoundException e) {
            System.out.println("Can't find Initable3");
            System.exit(1);
        }
        System.out.println("After creating Initable3 ref");
        System.out.println(Initable3.staticNonFinal);
    }

}
