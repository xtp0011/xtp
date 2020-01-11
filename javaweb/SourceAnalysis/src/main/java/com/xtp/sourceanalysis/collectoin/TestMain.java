package com.xtp.sourceanalysis.collectoin;

public class TestMain {
    public static void main(String[] args){
       // System.out.println(test(0));
       // Cat cat = new Cat();
    }

    public static int test(int a){
        try {
            System.out.println(a);
            return a+1;
        }catch (Exception e){
            return a+2;
        }finally {
            return a+3;
        }
    }
}
