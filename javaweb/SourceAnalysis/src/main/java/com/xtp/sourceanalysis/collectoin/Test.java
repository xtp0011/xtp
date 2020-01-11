package com.xtp.sourceanalysis.collectoin;

public class Test {
    public static void main(String[] args){
       /* int a = 0;
        int b =0;
        int c = 0;
        int d =0;
        a++;

        System.out.println("a="+a++);
        System.out.println("b="+b);
        System.out.println(c++);
        System.out.println(++d);

        System.out.println(result());*/
       /* System.out.println(result1());*/


        System.out.println(result4());

    }

    public static boolean result(){
        try {
            return true;
        }catch (Exception e){
            return true;
        }finally {
            return false;
        }
    }

    public static boolean result2(){
        boolean flag = false;
        try {
            return !flag;
        }catch (Exception e){
            flag= true;
        }finally {
            flag=false;
        }
        return flag;
    }



    public static int result1(){
        int i=0;
        int b=0;
        try {
            return ++b;
        }catch (Exception e){
            return b++;
        }finally {
            return i;
        }
    }


    public static int result4(){
        int i=0;
        int b=0;
        try {
            return ++b;
        }catch (Exception e){
            return b++;
        }finally {
           b=--b;
        }
    }
}
