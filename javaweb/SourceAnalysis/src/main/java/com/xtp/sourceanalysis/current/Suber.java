package com.xtp.sourceanalysis.current;

class Super{
    public Super() {
        System.out.println("supper");
    }
}

public class Suber extends Super{
    public Suber() {
        System.out.println("suber");
        //super();
    }

    public static void main(String[] args){
        new Suber();
    }
}
