package com.xtp.sourceanalysis.collectoin;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 java8引入了一个新的操作符"->"，该操作符将表达式拆分为两部分：

 左侧：Lambada表示表达式的参数列表

 右侧：Lambda表示所需要执行的功能
 */
public class LamdaTest {

    public static void main(String[] args){
        List<Integer> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add(i);
        }

        list.stream().forEach(i ->System.out.print(i+"\t"));
    }


    //语法格式一：无参数，无返回值
    //() -> System.out.println("Hello Lambda!");
    @Test
    public void test1(){
        Runnable r = () -> System.out.println("hello word");
    }

    //语法格式二:有一个参数，无返回值。
    @Test
    public void test2(){
        Consumer<String> consumer = (x) -> System.out.println(x);
        consumer.accept("我是帅哥");
    }


    //语法格式三:若值有一个参数，小括号可以省略不写。
    @Test
    public void test3(){
        Consumer<String> consumer = x -> System.out.println(x);
        consumer.accept("我是帅哥");
    }

    //语法格式四:有二个以上参数，有返回值，并且Lambda体中有多条语句。
    @Test
    public void test4(){
        Comparator<Integer> comparable = (x , y) -> {
            System.out.println("函数式接口");
            return Integer.compare(x,y);
        };
        System.out.println(comparable);
    }



   // 语法格式五:有二个以上参数，有返回值，并且Lambda体中只有一条语句，
   // return 和大括号都可以不写；
    @Test
    public void test5(){
        Comparator<Integer> comparable = (x , y) -> Integer.compare(x,y);
        System.out.println(comparable);
    }

    //语法格式六:lambda表达式的参数列表数据类型可以省略不写，
    // 因为jvm编译器通过上下文推断出数据类型，即“类型推断”。
    @Test
    public void test6() {
        Comparator<Integer> comparable = (Integer x ,Integer y) -> Integer.compare(x,y);
    }
}
