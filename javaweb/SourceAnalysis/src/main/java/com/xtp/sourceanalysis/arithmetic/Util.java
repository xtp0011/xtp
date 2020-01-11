package com.xtp.sourceanalysis.arithmetic;

import java.util.Arrays;
import java.util.Random;

public class Util {

    public static final String OLDMES = "排序前 : ";

    public static final String NEWMES = "排序后 : ";

    public static int[] randomArray(int len){
        Random random = new Random();
        int[] arr = new int[len];
        for(int i=0;i<len;i++){
            arr[i] = random.nextInt(100);
        }
        print(arr,OLDMES);
        return arr;
    }

    private static void print(int[] arr , String type){
        System.out.println(type+""+Arrays.toString(arr));
    }

    public static void print(int[] arr){
        print(arr,NEWMES);
    }

}
