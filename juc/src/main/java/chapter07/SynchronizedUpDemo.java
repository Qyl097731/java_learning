package chapter07;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * @author qyl
 * @program SynchronizedUpDemo.java
 * @Description TODO
 * @createTime 2022-08-02 15:43
 */

public class SynchronizedUpDemo {

    static Object o = new Object();

    public static void main(String[] args) throws InterruptedException {
        updateByHash();
    }

    public static void unLock() {
        System.out.println("10进制：   " + o.hashCode());
        System.out.println("16进制：   " + Integer.toHexString(o.hashCode()));
        System.out.println("2进制：   " + Integer.toBinaryString(o.hashCode()));

        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }

    public static void biased() throws InterruptedException {

        TimeUnit.SECONDS.sleep(5);
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        System.out.println("=========================================");

        new Thread(() -> {
            synchronized (o) {
                System.out.println(ClassLayout.parseInstance(o).toPrintable());
            }
        }).start();


    }

    public static void lightLock() {

        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        System.out.println("=========================================");

        new Thread(() -> {
            synchronized (o) {
                System.out.println(ClassLayout.parseInstance(o).toPrintable());
            }
        }).start();
    }

    public static void updateByHash() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        synchronized (o){
            o.hashCode();
            System.out.println("升级为轻量级锁");
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }


}
