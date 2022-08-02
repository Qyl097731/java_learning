package chapter04;

import lombok.val;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description
 * @date:2022/7/31 16:00
 * @author: qyl
 */
public class LockSupportDemo {
    static Object object = new Object();

    public static void main(String[] args) {
        lockSupportMethod();
    }

    public static void waitNotifyMethod() {
        new Thread(() -> {
            synchronized (object) {
                System.out.println(Thread.currentThread().getName() + "\t-----come in");
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t----被唤醒");

            }
        }, "t1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            synchronized (object) {
                object.notify();
                System.out.println(Thread.currentThread().getName() + "\t----发出唤醒通知");
            }
        }, "t2").start();
    }

    public static void lockConditionMethod() {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();


        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t-----come in");
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            System.out.println(Thread.currentThread().getName() + "\t----被唤醒");
        }, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            lock.lock();
            try {
                condition.signal();
                System.out.println(Thread.currentThread().getName() + "\t----发出唤醒通知");
            } finally {
                lock.unlock();
            }
        }, "t2").start();
    }

    public static void lockSupportMethod() {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t-----come in");
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "\t----被唤醒");
        }, "t1");
        t1.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            LockSupport.unpark(t1);
            System.out.println(Thread.currentThread().getName() + "\t----发出唤醒通知");
        }, "t2").start();
    }
}
