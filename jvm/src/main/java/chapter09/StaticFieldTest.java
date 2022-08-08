package chapter09;

import java.util.concurrent.TimeUnit;

/**
 * projectName:  jvm
 * packageName: chapter09
 *
 * @author 邱依良
 * @date: 2022-07-28 23:31
 */
public class StaticFieldTest {
    private static byte[] arr = new byte[1024 * 1024 * 100];

    public static void main(String[] args) {
        System.out.println(StaticFieldTest.arr);
        try{
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
