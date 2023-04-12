package com.nju.concurrent.ch15;

import net.jcip.annotations.ThreadSafe;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description 使用ReentrantLock实现随机数字生成器
 * @date:2023/1/11 14:56
 * @author: qyl
 */
@ThreadSafe
public class ReentrantLockPseudoRandom {
    private final Lock lock = new ReentrantLock (false);
    private int seed;

    ReentrantLockPseudoRandom(int seed) {
        this.seed = seed;
    }

    public int nextInt(int n) {
        lock.lock ();
        try {
            int s = seed;
            seed = calculatedNext (s);
            int remainder = s % n;
            return remainder > 0 ? remainder : remainder + n;
        } finally {
            lock.unlock ();
        }
    }

    private int calculatedNext(int s) {
        try {
            TimeUnit.SECONDS.sleep (1);
        } catch (InterruptedException e) {
            throw new RuntimeException (e);
        }
        return s + 1;
    }
}
