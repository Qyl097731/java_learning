package chapter06;

import java.util.ArrayList;
import java.util.Random;

/**
 * projectName:  jvm
 * packageName: chapter06
 * date: 2022-07-24 16:21
 * author 邱依良
 */
public class HeapInstanceTest {
    byte[] buffer = new byte[new Random().nextInt(1024 * 1024)];

    public static void main(String[] args) {
        ArrayList<HeapInstanceTest> list = new ArrayList<>();
        while (true) {
            list.add(new HeapInstanceTest());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
