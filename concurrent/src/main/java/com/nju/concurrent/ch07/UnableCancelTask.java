package com.nju.concurrent.ch07;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @description 不可取消得任务在退出前保存中断
 * @date:2022/12/21 19:50
 * @author: qyl
 */
public class UnableCancelTask {
    @Test
    public void test() {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<> ( );
        new Thread (() -> produceInt (queue)).start ();
        getNextInt (queue);
    }

    private void produceInt(BlockingQueue<Integer> queue) {
        try {
            TimeUnit.SECONDS.sleep (10);
            queue.put (1);
        } catch (InterruptedException e) {
            throw new RuntimeException (e);
        }
    }

    public Integer getNextInt(BlockingQueue<Integer> queue) {
        boolean interrupted = false;
        try {
            while (true) {
                try {
                    return queue.take ();
                } catch (InterruptedException e) {
                    interrupted = true;
                }
            }
        }finally {
            if (interrupted) Thread.currentThread ().interrupt ();
        }
    }
}
