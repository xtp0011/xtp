package thread;

public class BlockConainerTest {
    public static void main(String[] args) {
        BlockConainer<Object> conainer = new BlockConainer<>();
        Producer producer = new Producer(conainer);
        Consumer consumer = new Consumer(conainer);
        producer.start();
        consumer.start();
    }

    private static void doMethode() {
        BlockConainer<Integer> b = new BlockConainer<>(3);
        b.put(100);
        b.put(200);
        b.put(300);
        System.out.println(b);
        //b.put(400);
        b.take();
        b.take();
        b.take();
        b.take();
        System.out.println(b);
    }
}
