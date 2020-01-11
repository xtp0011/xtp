package com.xtp;

import com.xtp.data.MyRBTree;
import com.xtp.util.Random;

import java.util.Arrays;
import java.util.HashMap;

public class TestMain {

/*
JJO:IiO
KJO:JiO
LJO:KiO
MJO:LiO
NJO:MiO
OJO:NiO
PJO:OiO
QJO:PiO
RJO:QiO
SJO:RiO
TJO:SiO



 */


    static String[] strings = {
            "轷龚",
            "轸齻",
            "轹齜",
            "轺鼽",
            "轻鼞",
            "轼黿",
            "载黠",
            "轾黁",
            "轿麢",
            "辀麃",
            "辁鹤",
            "辂鹅",
            "较鸦",
            "辄鸇",
            "辅鷨",
            "辆鷉",
    };

    public static void main(String[] args) {
        int cap = 5;
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        System.out.println(n+1);

       /* HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < strings.length; i++) {
            map.put(strings[i], i);
        }*/
    }

    private static void do2() {
        System.out.println(4 << 1);
        System.out.println(4 >> 1);
        // do1();
        HashMap<String, Integer> map = new HashMap<>();
        map.put("张三", 24);
        System.out.println(map);
    }

    private static void do1() {
        // RBTree tree = new RBTree();
        MyRBTree tree = new MyRBTree();
        int[] keys = Random.getRandoms(0, 100000, 10000);
        System.out.println(Arrays.toString(keys));
        for (int key : keys) {
            tree.insert(key, "key-----------" + key);
        }
        tree.printTreeLevel();
        System.out.println(tree.getHeight());
    }
}
