package com.xtp;

import java.util.*;

public class TestTwo {

   static Map map = new HashMap();

   static List list = new ArrayList();

    public static void main(String[] args){
        int len = 5;
        char[] chars = new char[len];
        createString(chars,len);
        System.out.println(map);
        System.out.println(list);
    }

    public static void createString(char[] chars ,int len){
        for(char i ='A';i<='z';i++){
            chars[len-1] = i;
            if(len>1){
                createString(chars,len-1);
            }else {
                test(chars);
            }
        }
        if(list.size()>8) return;
    }

    private static void test(char[] chars) {
        String str = new String(chars);
        int hash = str.hashCode();
        if(map.containsKey(hash)){
            if(!str.equals(map.get(hash))){
                list.add(str);
                if(list.size()>8) return;
            }
        }else {
            map.put(hash,str);
        }
    }
}
