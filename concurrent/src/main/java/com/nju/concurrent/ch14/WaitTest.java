package com.nju.concurrent.ch14;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description 测试wait 和 notify机制
 * @date:2023/1/3 23:02
 * @author: qyl
 */
public class WaitTest {
    private static boolean open = false;

    private synchronized void addWaitThread() throws InterruptedException {
        System.out.println ("lock");
        while (!open) {
            wait ();
        }
        System.out.println ("hello");
    }

    private synchronized void notifyWaitThread() {
        open = true;
        notify ();
        System.out.println ("notify thread");
    }

    public static void main(String[] args) {
        WaitTest test = new WaitTest ();
        new Thread (() -> {
            try {
                test.addWaitThread ();
            } catch (InterruptedException e) {
            }
        }, "t1").start ();
        new Thread (() -> {
            try {
                test.notifyWaitThread ();
            } catch (Exception e) {
            }
        }, "t2").start ();
    }
}
