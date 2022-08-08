package chapter17;

import java.util.ArrayList;

/**
 * @description
 * @date:2022/8/8 22:13
 * @author: qyl
 */
public class GCLogTest {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<byte[]> list = new ArrayList<>();

        for (int i = 0; i < 500; i++) {
            byte[] arr = new byte[1024 *1024];
            list.add(arr);
            Thread.sleep(50);
        }
    }
}
