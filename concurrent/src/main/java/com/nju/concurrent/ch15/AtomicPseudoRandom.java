package com.nju.concurrent.ch15;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description 使用AtomicInteger实现随机数字生成器
 * @date:2023/1/11 15:00
 * @author: qyl
 */
@ThreadSafe
public class AtomicPseudoRandom {
    private AtomicInteger seed;

    AtomicPseudoRandom(int seed) {
        this.seed = new AtomicInteger (seed);
    }

    public int nextInt(int n) {
        while (true) {
            int s = seed.get ();
            int nextSeed = calculateNext (s);
            if (seed.compareAndSet (s, nextSeed)) {
                int remainder = s % n;
                return remainder > 0 ? remainder : remainder + n;
            }
        }
    }

    private int calculateNext(int s) {
        try {
            TimeUnit.SECONDS.sleep (1);
        } catch (InterruptedException e) {
            throw new RuntimeException (e);
        }
        return s + 1;
    }
}
