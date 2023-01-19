package concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @description
 * @date:2023/1/19 15:37
 * @author: qyl
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore empty = new Semaphore (0);
        CountDownLatch count = new CountDownLatch (1);
        System.out.println ("permits " + empty.availablePermits ());
        for (int i = 0; i < 6; i++) {
            new Thread (()->{
                try {
                    count.await ();
                    empty.acquire ();
                    System.out.println ("\n" + "after acquire : permits " + empty.availablePermits ());
                    TimeUnit.SECONDS.sleep (1);
                } catch (InterruptedException e) {
                    throw new RuntimeException (e);
                }finally {
                    empty.release ();
                }
            },"t"+i).start ();
        }
        count.countDown ();
    }
}
