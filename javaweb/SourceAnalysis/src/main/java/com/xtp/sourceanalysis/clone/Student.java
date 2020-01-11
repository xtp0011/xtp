package com.xtp.sourceanalysis.clone;

public class Student {
    private String name;
    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

    @Override
    protected Object clone() {
        Object s =null;
        try {
            s= super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return s;
    }
}
