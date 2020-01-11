package thread;

import java.util.Arrays;

class Conainer{
    /**
     * 容器
     */
    private Object[] array;
    /**
     * 有效元素个数
     */
    private int size;

    public Conainer(){
        this(16);
    }

    public Conainer(int cap){
        array = new Object[cap];
    }

    /**
     * 向容器size放数据
     */
    public synchronized void add(Object data){
        //1、判断容器是否已满,满则扩容
        if(size==array.length);
        array = Arrays.copyOf(array,2*array.length);
        //2、方式数据
        array[size] = data;
        //3、修改size的值
        size++;
    }

    /**
     * 从容器取数据,从第0个位置取数据
     * @return
     */
    public synchronized Object get(){
        if(size==0) throw  new RuntimeException("容器为空");
        Object obj = array[0];
        System.arraycopy(array,1,array,0,size-1);
        size--;
        array[size]=null;
        return obj;
    }

    public synchronized int size(){
        return size;
    }

}

public class ConainerThreadTest {
    public static void main(String[] args) {
        Conainer c = new Conainer();
        c.get();
    }

}
