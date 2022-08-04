package chapter09;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description
 * @date:2022/8/2 23:11
 * @author: qyl
 */
public class AQSDemo {
    public static void main(String[] args) {
        // 非公平锁
        ReentrantLock reentrantLock = new ReentrantLock();

        // A B C三个顾客 银行A先到且占用很久
        new Thread(()->{
            reentrantLock.lock();
            try {
                System.out.println("------come in A");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                reentrantLock.unlock();
            }
        },"A").start();

        new Thread(()->{
            reentrantLock.lock();
            try {
                System.out.println("------come in B");
            }finally {
                reentrantLock.unlock();
            }
        },"B").start();

        new Thread(()->{
            reentrantLock.lock();
            try {
                System.out.println("------come in C");
            }finally {
                reentrantLock.unlock();
            }
        },"C").start();
    }
}
