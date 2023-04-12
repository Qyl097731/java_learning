package com.nju.concurrent.ch16;

import java.util.concurrent.CountDownLatch;

/**
 * @description 没有充分同步的程序可以产生令人惊讶的结果
 * @date:2023/1/13 17:15
 * @author: qyl
 */
public class PossibleReordering {
    static int x = 0,y = 0;
    static int a = 0,b = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread one = new Thread (() -> {
            a = 1;
            x = b;
        });
        Thread other = new Thread (()->{
            b = 1;
            y = a;
        });
        one.start ();other.start ();
        one.join ();other.join ();
        System.out.println (x + " " + y);
    }
}
