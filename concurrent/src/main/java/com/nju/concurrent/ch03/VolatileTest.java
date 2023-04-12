package com.nju.concurrent.ch03;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @description volatile 可见性 和 原子性测试
 * @date:2022/12/14 21:59
 * @author: qyl
 */
public class VolatileTest {
    // 可能死循环
    static int num1 = 1;
    // 可见性，一旦修改 就通知
    static volatile int num2 = 1;
    // 原子性
    static volatile int num3 = 1;

    /**
     * 可能死循环
     */
    @Test
    public void atomTest1() {
        new Thread (VolatileTest::addNumByOne1).start ( );
        while (num1 < 2) {
        }
        System.out.println (num1);
    }

    /**
     * 可见性体现
     */
    @Test
    public void atomTest2() {
        new Thread (VolatileTest::addNumByOne2).start ( );
        while (num2 < 2) {
        }
        System.out.println (num2);
    }

    /**
     * 不能保证原子性
     */
    @Test
    public void atomTest3() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool (10);
        for (int i = 0; i < 10; i++) {
            pool.submit (new Thread (() -> {
                for (int j = 0; j < 2000; j++) {
                    addNumByOne3();
                }
            },"t" + i));
        }
        pool.shutdown ();
        pool.awaitTermination (1,TimeUnit.SECONDS);
        System.out.println (num3);
    }

    static void addNumByOne1() {
        try {
            TimeUnit.SECONDS.sleep (1);
        } catch (InterruptedException e) {
            throw new RuntimeException (e);
        }
        num1++;
    }

    static void addNumByOne2() {
        try {
            TimeUnit.SECONDS.sleep (1);
        } catch (InterruptedException e) {
            throw new RuntimeException (e);
        }
        num2++;
    }

    static void addNumByOne3() {
        num3++;
    }

}

