package com.xtp.sourceanalysis.collectoin;

import java.util.LinkedList;
import java.util.List;

/**
 * 基于JDK1.8
 * @author 徐太平
 *	双向链表
 *
①LinkedList底层是一个双链表。是一个直线型的链表结构。 
②LinkedList内部实现了6种主要的辅助方法：void linkFirst(E e)、void linkLast(E e)、linkBefore(E e, Node<E> succ)、E unlinkFirst(Node<E> f)、E unlinkLast(Node<E> l)、E unlink(Node<E> x)。它们都是private修饰的方法或者没有修饰符，表明这里都只是为LinkedList的其他方法提供服务，或者同一个包中的类提供服务。在LinkedList内部，绝大部分方法的实现都是依靠上面的6种辅助方法实现的，所以，只要把这6个辅助方法自己实现了（或者理解了（最好还是自己实现一次）），LinkedList的基本操作也就掌握了。 
③如果自己能够实现LinkedList 的功能，对学习算法也很有帮助。毕竟算法是程序的灵魂么！ 
 */
public class LinkedListAnalysis {
	public static void main(String[] args) {
		LinkedList<String> list = new LinkedList<>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		list.add("e");
		list.remove();
		System.out.println(list);


		
	}

}
