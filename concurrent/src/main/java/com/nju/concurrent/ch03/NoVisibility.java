package com.nju.concurrent.ch03;

import net.jcip.annotations.NotThreadSafe;

/**
 * @description 没有同步的情况下共享变量
 * @date:2022/12/14 20:43
 * @author: qyl
 */
@NotThreadSafe
public class NoVisibility {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        public void run() {
            while (!ready) {
                Thread.yield ( );
            }
            System.out.println (number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread ().start ();
        number = 22;
        ready = true;
    }
}
