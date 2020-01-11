package com.xtp.sourceanalysis.thread;

/**
 * volatile 关键字
 * 1.多核或多CPU场景下保证共享标量的可见性
 * 2.禁止指令重排序操作
 * 3.不保证原子性
 *
 * happened-before 院长
 * 在JMM中如果一个操作的结果对另一个操作的结果是可见的，那么我们称这种方式为happened-before原则
 * 用来判断数据是否安全，判断多线程环境下变量的值是否可见的
 *
 *
 */
public class TestMain {
    static String content;
    public static void main(String[] args) throws Exception {
        /*Thread t = new Thread(){
            @Override
            public void run() {//运行状态
                content="hello word";
            }//run方法执行结束线程处于死亡
        };//新建状态
*/
        //run方法执行结束线程处于死亡
        Thread t = new Thread(() -> {//运行状态
            content="hello word";
        });//新建状态
        t.start();//就绪状态
        //Thread.sleep(1000);//主线程阻塞
        //t.join(10);//让t线程执行结束，主线程阻塞
        while (content==null){
            Thread.yield();//让当前线程放弃CPU，处于就绪状态
        }
        System.out.println(content.toUpperCase());
    }
}
