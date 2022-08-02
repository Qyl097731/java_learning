package chapter04;

import java.util.concurrent.TimeUnit;

/**
 * @description
 * @date:2022/7/31 12:52
 * @author: qyl
 */
public class DeadLockDemo {
    public static void main(String[] args) {
        final Object objectA = new Object();
        final Object objectB = new Object();
        new Thread(()->{
            synchronized (objectA){
                System.out.println(Thread.currentThread().getName() + "\t 自己持有A 希望获得B");
                try{
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (objectB){
                    System.out.println(Thread.currentThread().getName() + "\t 成功获得B");
                }
            }
        },"A").start();

        new Thread(()->{
            synchronized (objectB){
                System.out.println(Thread.currentThread().getName() + "\t 自己持有B 希望获得A");
                try{
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (objectA){
                    System.out.println(Thread.currentThread().getName() + "\t 成功获得A");
                }
            }
        },"B").start();
    }
}
