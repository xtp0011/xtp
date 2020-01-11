package com.xtp.sourceanalysis.collectoin;

/**
 * 
 * 
 * @author 徐太平
①HashSet内部通过使用HashMap的键来存储集合中的元素，而且内部的HashMap的所有值 都是null。
（因为在为HashSet添加元素的时候，内部HashMap的值都是PRESENT），
而PRESENT在实例域的地方直接初始化了，而且不允许改变。 
②HashSet对外提供的所有方法内部都是通过HashMap操作完成的，
所以，要真正理解HashSet的实现，只需要把HashMap的原理理解即可
 */
public class HashSetAnalysis {

}
