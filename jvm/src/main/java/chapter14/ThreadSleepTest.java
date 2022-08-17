package chapter14;

/**
 * @description
 * @date:2022/8/17 22:41
 * @author: qyl
 */
public class ThreadSleepTest {
    public static void main(String[] args) {
        System.out.println("hello -1");
        try {
            Thread.sleep(1000 * 60 * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("hello -2");
    }
}
