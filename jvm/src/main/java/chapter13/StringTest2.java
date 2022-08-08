package chapter13;

import java.util.concurrent.TimeUnit;

/**
 * @description
 * @date:2022/8/4 23:23
 * @author: qyl
 */
public class StringTest2 {
    public static void main(String[] args) {
        System.out.println("我来打酱油");
        try {
            TimeUnit.SECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
