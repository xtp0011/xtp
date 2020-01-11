package com.xtp.sourceanalysis.clone;

import java.io.Serializable;

public class Teacher implements Serializable {
    private String name;
    private int age;

    public Teacher(String name, int age) {
        this.name = name;
        this.age = age;
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}


