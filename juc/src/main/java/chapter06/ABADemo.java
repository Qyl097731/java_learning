package chapter06;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author qyl
 * @program ABADemo.java
 * @Description TODO
 * @createTime 2022-08-01 15:48
 */
public class ABADemo {
    static AtomicInteger atomicInteger = new AtomicInteger(1);

    static AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {

    }

    public static void abaHappen() {
        new Thread(() -> {
            atomicInteger.compareAndSet(1, 2);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicInteger.compareAndSet(2, 1);
        }, "t1").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicInteger.compareAndSet(1, 299) + "\t" + atomicInteger.get());
        }, "t2").start();
    }

    public static void abaSolve(){
        new Thread(() -> {
            int stamp = stampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 首次版本号为 " + stamp);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stampedReference.compareAndSet(100, 101, stampedReference.getStamp(), stampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t 2次版本号为 " + stampedReference.getStamp());

            stampedReference.compareAndSet(101, 100, stampedReference.getStamp(), stampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t 3次版本号为 " + stampedReference.getStamp());
        }, "t1").start();

        new Thread(() -> {
            int stamp = stampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 首次版本号为 " + stamp);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stampedReference.compareAndSet(100, 101, stamp, stamp + 1);
            System.out.println(stampedReference.compareAndSet(100, 101, stamp, stamp + 1) + "\t" + stampedReference.getReference() + "\t" + stampedReference.getStamp());
        }, "t2").start();
    }
}
