package thread;

/**
 * 多线程执行售票任务
 */
public class TicketTaskTest {
    static class TicketTask implements Runnable{
        int ticket = 10;
        @Override
        public void run() {
            doTicket();
        }

        private void doTicket() {
            while (true){
                //多线程在此代码块上同步排队执行（同步）
                synchronized (this){//保证操作的原子性
                    if(ticket<1) break;
                    String tname = Thread.currentThread().getName();
                    System.out.println(tname+" : "+ticket--);
                    sleep();
                }//排他锁（独占锁）
            }
        }

        private void sleep() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        System.out.println("Processors: "+runtime.availableProcessors());
        TicketTask task = new TicketTask();
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        Thread t3 = new Thread(task);
        Thread t4 = new Thread(task);
        t1.start();
        t2.start();
        t3.start();
        t4.start();


    }
}
