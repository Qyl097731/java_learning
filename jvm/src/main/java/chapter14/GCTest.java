package chapter14;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author qyl
 * @program GCTest.java
 * @Description GC测试
 * @createTime 2022-08-17 15:12
 */
public class GCTest {
    public static void main(String[] args) {
        ArrayList<byte[]> list = new ArrayList<>();
        try {

            TimeUnit.MILLISECONDS.sleep(3000);

            for (int i = 0; i < 1000; i++) {
                list.add(new byte[1024 * 100]);
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
