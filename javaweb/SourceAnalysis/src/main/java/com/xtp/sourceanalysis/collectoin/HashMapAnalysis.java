package com.xtp.sourceanalysis.collectoin;

import java.util.HashMap;

/**
    * 基于 JDK 1.8
 * @author 徐太平
 *	空参构造器只给加载因子赋默认值0.75
 *	调用put方法添加元素时来对数组进行扩容
 *	
 *	当数组容量大于阈值（容量*加载因子），就会扩容当前容量的两倍
	当链表长度大于8时，（前提数组容量比须大于等于64时）转为红黑树存储，
	当小于6时，转为链表存储(正常情况下)
 *
 *
	DEFAULT_INITIAL_CAPACITY：默认初识容量 1 << 4 ，也就是16，必须是2的整数次方。
	
	DEFAULT_LOAD_FACTOR：默认加载因子，大小为0.75。
	
	MAXIMUM_CAPACITY：最大容量， 2^ 30 次方。
	
	TREEIFY_THRESHOLD = 8 ：树形阈值，大于这个数就要树形化，也就是转成红黑树。
	
	MIN_TREEIFY_CAPACITY = 6 ：树形最小容量。
	
	table：哈希表的链接数组，对应桶的下标。
	
	entrySet：键值对集合。
	
	size：键值对的数量，也就是HashMap的大小。
	
	threshold：阈值，下次需要扩容时的值，等于 容量*加载因子。
	
	loadFactor：加载因子。

 */
public class HashMapAnalysis {
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
		HashMap<String,Integer> map = new HashMap<>();
		for(int i=0;i<strings.length;i++) {
			String string = strings[i];
			if(map.size()>10) break;
			map.put(string, i);
		}
		System.out.println("添加元素完成 !!!!!! 【 size: ["+map.size()+"] 】;此后删出元素.........");
		
		for(int i=0;i<strings.length;i++) {
			String string = strings[i];
			map.remove(string);
			System.out.println("删除后的map: "+map);
		}
	}

}
