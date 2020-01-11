package com.xtp.sourceanalysis.current;

import java.util.concurrent.locks.ReentrantLock;

public class TestMain {
    public static void main(String[] args){
        //do1();
        //do2();
        int i=1000;
        i--;
        ++i;
        System.out.println(i);
    }

    private static void do2() {
        Singletion singletion = Singletion.getSingletion();
        System.out.println(singletion);
    }

    private static void do1() {
        ReentrantLock lock = new ReentrantLock();
        int count = 0;
        for(int i=0;i<3;i++){
            count++;
            System.out.println("加锁次数："+count);
            lock.lock();
        }

        for(int i=0;i<count;i++){
            System.out.println("释放锁次数:"+i);
            lock.unlock();
        }
    }
}
