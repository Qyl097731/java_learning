package chapter14;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @description
 * @date:2022/8/17 23:07
 * @author: qyl
 */
public class HeapInstanceTest {
    byte[] buffer = new byte[new Random().nextInt(1024 * 100)];

    public static void main(String[] args) {
        try {
            Thread.sleep(5000);

            List<HeapInstanceTest> list = new ArrayList<>();
            while (true) {
                list.add(new HeapInstanceTest());
                Thread.sleep(10);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
