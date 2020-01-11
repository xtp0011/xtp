package thread;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


class Producer extends Thread{
    private BlockConainer<Object> conainer;
    public Producer(BlockConainer<Object> conainer ){
        this.conainer=conainer;
    }

    @Override
    public void run() {
        int i = 1 ;
        while (true){
            conainer.add(i);
            i++;
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer extends Thread{
    private BlockConainer<Object> conainer;
    public Consumer(BlockConainer<Object> conainer){
        this.conainer=conainer;
    }

    @Override
    public void run() {
        while (true){
            Object result = conainer.get();
            System.out.println(result);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

/**
 * 有界消息队列，用于存储消息
 * 1）数据结构：数组（线性数据）
 * 2）具体算法：先进先出
 * @param <T>
 */
public class BlockConainer<T> {

    //JDK1.5以后引入的重入锁（相对于synchronized灵活性更好）
    private ReentrantLock lock = new ReentrantLock(true);//true 表示公平锁，默认是非公平锁
    private Condition producerCondition = lock.newCondition();//通讯条件
    private Condition consumerCondition = lock.newCondition();//通讯条件

    public void add(T t){
        //System.out.println("add ");
        lock.lock();
        try {
            while (size==array.length){
                try {
                    producerCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            array[size]=t;
            size++;
            consumerCondition.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public T get(){
        //System.out.println("get");
        lock.lock();
        try {
            while (size==0){
                try {
                    consumerCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Object object = array[0];
            System.arraycopy(array,1,array,0,size-1);
            size--;
            array[size]=null;
            producerCondition.signalAll();
            return (T)object;
        }finally {
            lock.unlock();
        }
    }


    @Override
    public String toString() {
        return "BlockConainer{" +
                "array=" + Arrays.toString(array) +
                '}';
    }

    private Object[] array;//用与存放数据的数组
    private int size ;//记录元素个数
    public BlockConainer(){
        this(16);
    }
    public BlockConainer(int cap){
        array = new Object[cap];//每个元素个数默认值为null
    }

    /**
     * 生产者线程通过put方法向容器内放数据
     * 数据永远存放在size位置
     * 说明：实例方法内部的this永远指向调用
     * 此方法的当前对象（当前实例）
     * 注意：静态方法中没有this，this只能
     * 应用在实例方法，构造方法，实例代码块中
     * @param t
     */
    public synchronized void put(T t){//同步锁:this
        //1、判断容器是否已满，满则等待
        while (size==array.length) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //2、放数据
        array[size]=t;
        //3、有效元素个数加1
        size++;
        //4.通知消费者取数据
        this.notifyAll();
    }

    /**
     * 消费者取数据
     * 位置：永远取下标为0的位置的数据
     * @return
     */
    public synchronized T take(){
        //1、判读容器是否为空，空则等待
        while (size==0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //2、取数据
        Object object = array[0];
        //3、移动元素
        System.arraycopy(array,1,array,0,size-1);
        //4、有效元素个数减1
        size--;
        //5、将size位置的数据置为空
        array[size]=null;
        //6、通知生产者生产数据
        this.notifyAll();//通知具备相通锁的wait线程
        return (T)object;
    }

}
