package thread;

public class TestTread {
    static String content;
    public static void main(String[] args) throws InterruptedException {
       Thread t =  new Thread(){
            public void run(){ //运行状态
                content="hello word";
            }//run方法执行结束线程处于死亡状态
        };//新建状态
        t.start();//就绪状态（可运行状态）
        //Thread.sleep(1000);//主线程阻塞
        //t.join();让t线程执行结束，主线程阻塞
        //while (content==null);
        while (content==null){
            Thread.yield();//让当前线程放弃cpu,处于阻塞状态
        }
        System.out.println(content.toUpperCase());
    }
}
