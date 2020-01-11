package com.xtp.sourceanalysis.clone;

public class TestMain {
    public static void main(String[] args) {
        Student s1 = new Student("张三",22);
        Object s2 = s1.clone();

        System.out.println(s1==s2);
        System.out.println(s2);
/*
        Teacher t1 = new Teacher("张三",22);
        Object t2 = t1.clone();
        System.out.println(t1==t2);
        System.out.println(t2);*/


    }
}
