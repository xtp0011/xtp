package memory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * jps 查看jvm运行的java类型
 * jmap -heap 进程号 查看对象内存映射或堆内存细节
 * jmap -hoiso:live 进程号  查看Java中占用内存字节大小
 *
 * jstack 查看Java虚拟机当前时刻的线程快照
 *
 *
 */
public class TestCommand {
    public static void main(String[] args) {

        /**
         * 使用 JVM 参数                                   最大堆  最大堆   eden区
         *  1. -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xmx1G -Xms1G -Xmn500M
         *  2. -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xmx20M -Xms20M -Xmn4M
         */
        byte[] b1 = new byte[1024*1024];
        byte[] b2 = new byte[1024*1024];
        byte[] b3 = new byte[1024*1024];
        byte[] b4 = new byte[1024*1024];
    }

    private static void test2() {
        List<Integer> list1 = new ArrayList<>(Arrays.asList(2,4,6,8,10));
        List<Integer> list2 = new ArrayList<>(Arrays.asList(1,3,5,7,9));
        Thread thread1 = new Thread(new SyncTask02(list1,list2,2));
        Thread thread2 = new Thread(new SyncTask02(list2,list1,9));
        thread1.start();
        thread2.start();
    }

    private static void test1() {
        byte[] bytes;
        while (true){
            bytes = new byte[1024*1024];
        }
    }
}

class SyncTask02 implements Runnable{

    private List<Integer> from ;
    private List<Integer> to;
    private Integer target ;

    public SyncTask02(List<Integer> from ,List<Integer> to ,Integer target){
        this.from=from;
        this.to=to;
        this.target=target;
    }

    @Override
    public void run() {
        moverListItem(from,to,target);
    }

    private static void moverListItem(List<Integer> from, List<Integer> to, Integer item) {
        log("attempting lock for list",from);
        synchronized (from){
            log("lock acquired for list",from);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log("attempting lock for list",to);
            synchronized (to){
                log("lock acquired for list",to);
                if(from.remove(item)){
                    to.add(item);
                }
                log("move item to list",to);
            }
        }
    }

    private static void log(String msg, Object target) {
        System.out.println(
                Thread.currentThread().getName()+" : "+msg+"   "+
                        System.identityHashCode(target));
    }
}
