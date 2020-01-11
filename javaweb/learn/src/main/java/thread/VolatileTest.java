package thread;

class Looper{
    private  boolean isStop ;
    public void loop(){
        for(;;){
            if(isStop) break;
        }
    }
    public void stop(){
        isStop=true;
    }
}

public class VolatileTest {
    public static void main(String[] args) throws InterruptedException {
        Looper looper = new Looper();
        /*
         *Thread t = new Thread(){
            @Override
            public void  run (){
                looper.loop();
            }
        };
         */
        Thread t = new Thread(() -> looper.loop());
        t.start();
        t.join(3);
        looper.stop();
    }
}
