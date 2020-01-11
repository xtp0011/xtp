package com.xtp.sourceanalysis.collectoin;

import java.util.ArrayList;

/**
 * 基于JDK1.8
 * @author 徐太平
 * 	默认初始容量位10，默认是一个空数组
 * DEFAULT_CAPACITY = 10;//默认的初始容量
 * 
 * //默认的容量
    private static final int DEFAULT_CAPACITY = 10;

    //定义了一个空的数组，用于在用户初始化代码的时候传入的容量为0时使用。
    private static final Object[] EMPTY_ELEMENTDATA = {};

    //同样是一个空的数组，用于默认构造器中，赋值给顶层数组elementData。
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    //底层数组，ArrayList中真正存储元素的地方。
    transient Object[] elementData;

    //用来表示集合中含有元素的个数
    private int size;

 * 
① ArrayList在我们日常开发中随处可见，所以建议大家可以自己手动实现一个ArrayList，实在写不出来可以模仿一下ArrayList么。

② 由于ArryList随机存储，底层是用的一个数组作为存放元素的，所以在遍历ArrayList的时候，使用get（）方法的效率要比使用迭代器的效率高。

③在ArrayList中经常使用的一个变量modCount，它是在ArrayList的父类AbstractList中定义的一个protected变量，该变量主要在多线程的环境下，如果使用迭代器进行删除或其他操作的时候，需要保证此刻只有该迭代器进行修改操作，一旦出现其他线程调用了修改modCount的值的方法，迭代器的方法中就会抛出异常。究其原因还是因为ArrayList是线程不安全的。 
④ 在ArrayList底层实现中，很多数组中元素的移动，都是通过本地方法System.arraycopy实现的，该方法是由native修饰的。 
⑤ 在学习源码的过程中，如果有看不懂的方法，可以自己写一个小例子调用一下这个方法，然后通过debug的方式辅助理解代码的含义。当然啦，有能力的最好自己实现一下。（不过有些方法确实设计的超级精巧，直接读代码还看不懂，只能通过debug辅助学习源代码，更别提写这些方法了。。。。） 
⑥ 不过我们在操作集合的过程中，尽量使用使用基于Stream的操作，这样能够不仅写起来爽，看起来更爽！真的是谁用谁知道
*/

public class ArrayListAnalysis {
	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<>();
		list.add("A");
	}

}
