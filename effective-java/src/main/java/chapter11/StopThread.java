package chapter11;

import java.util.concurrent.TimeUnit;

/**
 * @description
 * @date:2022/9/7 15:29
 * @author: qyl
 */
public class StopThread {
    private static boolean stopRequested;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            int i = 0;
            while(!stopRequested){
                i++;
            }
        },"t1");
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        stopRequested = true;
    }

}
