package com.xtp.sourceanalysis.oop.duotai;

/**
 *
 *

 只有非private方法才可以被覆盖，但是还需要密切注意覆盖private方法的现象，
 这时虽然编译器不会报错，但是也不会按照我们所期望的来执行，
 即覆盖private方法对子类来说是一个新的方法而非重载方法。
 因此，在子类中，新方法名最好不要与基类的private方法采取同一名字（虽然没关系，
 但容易误解，以为能够覆盖基类的private方法）。


 •	Java类中属性域的访问操作都由编译器解析，因此不是多态的。
 父类和子类的同名属性都会分配不同的存储空间，

 */

class Supper{
    public int field = 0;

    public int getField(){
        return field;
    }
}

class Sub extends Supper{
    public int field = 1;

    @Override
    public int getField() {
        return field;
    }

    public int getSupperField(){
        return super.field;
    }
}



public class FieldAccess {

    public static void main(String[] args){
        Supper sup = new Sub();
        System.out.println("sup.filed = " + sup.field +
                ", sup.getField() = " + sup.getField());


        Sub sub = new Sub();
        System.out.println("sub.filed = " + sub.field +
                ", sub.getField() = " + sub.getField() +
                ", sub.getSuperField() = " + sub.getSupperField());

    }




}
