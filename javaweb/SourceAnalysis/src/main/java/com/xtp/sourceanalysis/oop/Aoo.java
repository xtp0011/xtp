package com.xtp.sourceanalysis.oop;

/**
 * 重写方法时，返回可以不同，但子类的返回值类型比须等于或小于父类
 *
 *
 * synchronized
 * synchronized
 */
public class Aoo {
    public static void main(String[] args){

    }
}

class Boo{

    public Object text(){
        System.out.println("Boo");
        return 1;
    }
}

class Coo extends Boo{
    @Override
    public Number text() {
        System.out.println("Coo");
        return 2;
    }
}

class Doo extends Coo{
    @Override
    public Integer text() {
        return 3;
    }
}




