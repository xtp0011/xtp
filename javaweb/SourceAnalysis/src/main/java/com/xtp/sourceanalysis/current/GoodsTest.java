package com.xtp.sourceanalysis.current;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GoodsTest{
    public static void main(String[] args){
        Goods goods= new Goods(10);
        List<Thread> threads = new ArrayList<>();
        for(int i=0;i<10;i++){
            threads.add(new Thread(new Prouduct(goods), "生产者"+i));
            threads.add(new Thread(new Customer(goods), "消费者"+i));
        }

        for(Thread thread: threads){
            thread.start();
        }
    }

}

 class Goods {
    private Integer count;
    private Integer maxCount;
    private Lock lock = new ReentrantLock();
    private Condition productCondition = lock.newCondition();
    private Condition customerCondition = lock.newCondition();

    public Goods(Integer maxCount) {
        this.count=0;
        this.maxCount = maxCount;
    }

    public void set(){
        try {
            lock.lock();
            while (count>=maxCount){
                Thread.sleep(500);
                System.out.println("商品已饱和");
                productCondition.await();
            }
            count++;
            Thread.sleep(500);
            System.out.println(Thread.currentThread().getName() + "产品现有" + count);
            customerCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void get(){
        try {
            lock.lock();
            while (count<1){
                System.out.println("产品已售完");
                customerCondition.await();
            }
            count--;
            Thread.sleep(200);
            System.out.println(Thread.currentThread().getName() + "商品还剩" + count);
            productCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }



}


class Prouduct implements Runnable{

    private Goods goods;

    public Prouduct(Goods goods) {
        this.goods = goods;
    }

    @Override
    public void run() {
        while (true){
            goods.set();
        }

    }
}

class Customer implements Runnable{
    private Goods goods;
    public Customer(Goods goods) {
        this.goods = goods;
    }
    @Override
    public void run() {
        while (true){
            goods.get();
        }

    }
}


