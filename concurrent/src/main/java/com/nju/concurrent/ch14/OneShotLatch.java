package com.nju.concurrent.ch14;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @description 二元闭锁使用AQS
 * @date:2023/1/5 21:18
 * @author: qyl
 */
@ThreadSafe
public class OneShotLatch {

    private final Sync sync = new Sync ();

    public void signal(){
        sync.releaseShared (0);
    }

    public void await() throws InterruptedException {
        sync.acquireInterruptibly (0);
    }

    private class Sync extends AbstractQueuedSynchronizer {
        protected int tryAcquireShared(int ignored) {
            // 如果闭锁打开则成功，否则失败
            return getState () == 1 ? 1 : -1;
        }
        protected boolean tryReleaseShared(int ignored){
            setState (1); // 闭锁打开
            return true;  // 该操作能执行， 其他线程可以获得闭锁
        }
    }
}
