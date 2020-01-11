package memory;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆溢出
 */
public class TestOOM {
    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        try {
            List<byte[]> list = new ArrayList<>();
            for (int i=0;i<10000;i++){
                list.add(new byte[1024*1024]);
            }
        }finally {
            long t2= System.currentTimeMillis();
            System.out.println(t2-t1);
        }


    }
}
