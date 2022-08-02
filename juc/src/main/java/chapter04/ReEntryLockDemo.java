package chapter04;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * projectName:  juc
 * packageName: chapter04
 *
 * @author 邱依良
 * @date: 2022-07-27 23:47
 */
public class ReEntryLockDemo {
    public static void main(String[] args) {
//        new ReEntryLockDemo().m1();
        new Thread(()->{
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName()+"\t------come in外层");
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName()+"\t------come in内层");
                }finally {
                    lock.unlock();
                }
            }finally {
                lock.unlock();
            }
        },"t1").start();

        new Thread(()->{
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName()+"\t------come in外层");
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName()+"\t------come in内层");
                }finally {
                    lock.unlock();
                }
            }finally {
                lock.unlock();
            }
        },"t2").start();
    }

    static Lock lock = new ReentrantLock();


    public synchronized void m1(){
        System.out.println(Thread.currentThread().getName()+"\t----come in");
        m2();
        System.out.println(Thread.currentThread().getName()+"\t----end");
    }

    public synchronized void m2(){
        System.out.println(Thread.currentThread().getName()+"\t----come in");
        m3();
        System.out.println(Thread.currentThread().getName()+"\t----end");
    }

    public synchronized void m3(){
        System.out.println(Thread.currentThread().getName()+"\t----come in");
    }

    private static void reEntryM1(){
        final Object object = new Object();
        new Thread(() -> {
            synchronized (object) {
                System.out.println(Thread.currentThread().getName() + "\t-----外层调用");
                synchronized (object) {
                    System.out.println(Thread.currentThread().getName() + "\t-----中层调用");
                    synchronized (object) {
                        System.out.println(Thread.currentThread().getName() + "\t-----内层调用");
                    }
                }
            }
            ;
        }, "synchronized").start();
    }
}
