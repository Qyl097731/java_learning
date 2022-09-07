package chapter11;

import java.util.concurrent.TimeUnit;

/**
 * @description
 * @date:2022/9/7 15:33
 * @author: qyl
 */
public class StopThread2 {
    private static boolean stopRequested;

    private static synchronized void requestStop(){
        stopRequested = true;
    }

    private static synchronized  boolean stopRequested(){
        return stopRequested;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            int i = 0 ;
            while (!stopRequested()){
                i++;
            }
        },"t1");
        t1.start();

        TimeUnit.SECONDS.sleep(1);
        requestStop();
    }
}
