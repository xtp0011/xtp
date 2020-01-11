package com.xtp.sourceanalysis.oop.duotai;


/**
 •	Java中除了static方法和final方法（private方法本质上属于final方法，
    因为不能被子类访问）之外，其它所有的方法都是动态绑定，

 o	final方法会使编译器生成更有效的代码，
 这也是为什么说声明为final方法能在一定程度上提高性能（效果不明显）。

 o	如果某个方法是静态的，它的行为就不具有多态性：

 *
 *
 *
 */
class StaticSupper{
    public static String staticGet(){
        return "Base staticGet()";
    }

    public String dynamicGet(){
        return "Base dynamicGet()";
    }
}

class StaticSub extends StaticSupper{
    public static String staticGet(){
        return "Derived  staticGet()";
    }

    public String dynamicGet(){
        return "Derived  dynamicGet()";
    }

}



public class StaticPolymorphism {
    public static void main(String[] args){
        StaticSupper s = new StaticSub();
        System.out.println(s.staticGet());
        System.out.println(s.dynamicGet());
    }
}
