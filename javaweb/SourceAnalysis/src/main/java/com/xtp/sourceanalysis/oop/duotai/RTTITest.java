package com.xtp.sourceanalysis.oop.duotai;

/**
 * o	一种是“传统的”RTTI，它假定我们在编译时已经知道了所有的类型，比如Shape s = (Shape)s1；
 *
 * o	另一种是“反射”机制，它运行我们在运行时发现和使用类的信息，即使用Class.forName()。
 *
 * o	其实还有第三种形式，就是关键字instanceof，它返回一个bool值，它保持了类型的概念，
 * 它指的是“你是这个类吗？或者你是这个类的派生类吗？”。而如果用==或equals比较实际的Class对象，
 * 就没有考虑继承—它或者是这个确切的类型，或者不是。
 *
 *
 *
 */

interface HasBatteries{}

interface WaterProof{}

interface Shoots{}

class Toy{
    Toy(){}
    Toy(int i){}
}

class FancyToy extends Toy implements HasBatteries,WaterProof,Shoots{
    FancyToy(){
        super(1);
    }
}

public class RTTITest {
    static void printInfo(Class cc){
        System.out.println("Class name: " + cc.getName() + ", is interface? [" + cc.isInterface() + "]");
        System.out.println("Simple name: " + cc.getSimpleName());
        System.out.println("Canonical name: " + cc.getCanonicalName());
    }

    public static void main(String[] args){
        Class<?> c = null;
        try {
             c = Class.forName("com.xtp.sourceanalysis.oop.duotai.FancyToy");
        } catch (ClassNotFoundException e) {
            System.out.println("Can't find FancyToy");
            System.exit(1);
        }
        printInfo(c);

        for (Class<?> face:c.getInterfaces()){
            printInfo(face);
        }

        Class<?> supper = c.getSuperclass();
        Object obj = null;
        try {
            obj = supper.newInstance();
        } catch (InstantiationException e) {
            System.out.println("Can't Instantiate");
            System.exit(1);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.out.println("Can't access");
            System.exit(1);
            e.printStackTrace();
        }

        printInfo(obj.getClass());
    }
}
