package thread;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class dataUtils{
    private static ThreadLocal<SimpleDateFormat> td = new ThreadLocal<>();
    //private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    public static String parse(Date date){
        //从当前线程获取对象
        SimpleDateFormat sdf = td.get();
        if(sdf==null)
        td.set(new SimpleDateFormat("yyyy/MM/dd"));
        return sdf.format(date);
    }
}

class Counter1 {
    private volatile int count ;
    //公平锁
    private Lock lock = new ReentrantLock(true);
    public  void Count(){
        lock.lock();//加锁
        try {
            count++;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();//解锁
        }
    }

    public int getCount() {
        return count;
    }
}

class Counter2{
    //底层使用CAS算法(基于CPU硬件来实现)比较和交换
    //1、内存值  2、期望值   3、要更新的值
    private AtomicInteger at = new AtomicInteger();
    public void count(){
        at.incrementAndGet();
    }
}



public class CounterTreadTest {
    static class Counter implements Runnable{
        private volatile int count ;
        //synchronized JDK1.6之前性能非常差
        public synchronized void doCount(){//非公平锁
            count++;//此操作非原子操作
        }

        @Override
        public void run() {
            for (int i =0;i<10;i++){
                doCount();
            }
        }

        public int getCount() {
            return count;
        }
    }



    public static void main(String[] args) {
        Counter counter = new Counter();
        List<Thread> list = new ArrayList<>();
        for (int i=0;i<100;i++){
            list.add(new Thread(counter));
        }
        for(Thread t : list){
            t.start();
        }

        //判断线程的数量是否大于1


        while (Thread.activeCount()>2){
            Thread.yield();
        }
        System.out.println(counter.getCount());
    }
}
