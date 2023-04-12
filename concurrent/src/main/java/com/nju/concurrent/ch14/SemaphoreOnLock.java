package com.nju.concurrent.ch14;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description 通过Lock实现Semaphore
 * @date:2023/1/3 23:19
 * @author: qyl
 */
@ThreadSafe
public class SemaphoreOnLock {
    private final Lock lock = new ReentrantLock ();
    private final Condition permitsAvailable = lock.newCondition ();
    @GuardedBy ("lock") private int permits;

    SemaphoreOnLock(int permits) {
        lock.lock ();
        try {
            this.permits = permits;
        }finally {
            lock.unlock ();
        }
    }

    public void acquire() throws InterruptedException {
        lock.lock ();
        try {
            while (permits <= 0){
                permitsAvailable.await ();
                --permits;
            }
        }finally {
            lock.unlock ();
        }
    }

    public void release(){
        lock.lock ();
        try {
            ++permits;
            permitsAvailable.signal ();
        }finally {
            lock.unlock ();
        }
    }
}
