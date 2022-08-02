package chapter04;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @description
 * @date:2022/7/31 15:00
 * @author: qyl
 */
public class InterruptDemo {
    static volatile boolean isStop = false;
    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public static void main(String[] args) {
        stopByInterrupted();
    }

    /**
     * 通过volatile来进行中断线程
     */
    public static void stopByVolatile() {
        new Thread(() -> {
            while (true) {
                if (isStop) {
                    System.out.println(Thread.currentThread().getName() + "\t isStop被修改为true 程序停止");
                    break;
                }
                System.out.println("hello volatile");
            }
        }, "t1").start();

        //暂停毫秒
        try {
            TimeUnit.MICROSECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            isStop = true;
        }, "t2").start();
    }

    /**
     * 通过AtomicBoolean进行终端线程
     */
    public static void stopByAtomicBoolean() {
        new Thread(() -> {
            while (true) {
                if (atomicBoolean.get()) {
                    System.out.println(Thread.currentThread().getName() + "\t atomicBoolean 被修改为true 程序停止");
                    break;
                }
                System.out.println("hello atomicBoolean");
            }
        }, "t1").start();

        //暂停毫秒
        try {
            TimeUnit.MICROSECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            atomicBoolean.set(true);
        }, "t2").start();
    }

    /**
     * 通过 interrupted来中断线程
     */
    public static void stopByInterrupted() {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "\t interrupted 被修改为true 程序停止");
                    break;
                }
                System.out.println("hello interrupted api");
            }
        }, "t1");
        t1.start();

        //暂停毫秒
        try {
            TimeUnit.MICROSECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(t1::interrupt, "t2").start();
    }
}
