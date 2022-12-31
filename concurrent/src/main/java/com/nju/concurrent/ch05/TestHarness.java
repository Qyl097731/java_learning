package com.nju.concurrent.ch05;

import java.util.concurrent.CountDownLatch;

/**
 * @description 闭锁 模拟瞬时并发
 * @date:2022/12/19 18:41
 * @author: qyl
 */
public class TestHarness {
    static int num = 0;

    public static void main(String[] args) throws InterruptedException {
        if (args.length == 1) {
            System.out.println (timeTasks (Integer.parseInt (args[0]), () -> num++));
        }
    }

    public static long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch (1);
        final CountDownLatch endGate = new CountDownLatch (nThreads);
        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread (() ->{
                try {
                    startGate.await ();
                    try {
                        task.run ();
                    }finally {
                        endGate.countDown ();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException (e);
                }
            });
            t.start ();
        }
        long start = System.nanoTime ();
        startGate.countDown ();
        endGate.await ();
        long end = System.nanoTime ();
        return (end - start)/1000;
    }
}
