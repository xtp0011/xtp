package com.xtp.util;

import java.util.ArrayList;
import java.util.List;

public class Random {

    /**
     * 产生不重复的随机数组
     * @param min
     * @param max
     * @param count
     * @return
     */
    public static int[] getRandoms(int min,int max,int count){
        int[] randoms = new int[count];
        List<Integer> listRandom = new ArrayList<>();
        if(count>max-min+1){
            return null;
        }
        for(int i=min;i<max;i++){
            listRandom.add(i);
        }

        for(int i=0;i<count;i++){
            int index = getRandoms(0,listRandom.size()-1);
            randoms[i] = listRandom.get(index);
            listRandom.remove(index);
        }
        return randoms;
    }

    public static int getRandoms(int min, int max) {
        java.util.Random random = new java.util.Random();
        int index = random.nextInt(max);
        return index>min?index:0;
    }

    public static void main(String args[]){
        int a = getRandoms(0,1000);
        System.out.println(a);
    }

}
