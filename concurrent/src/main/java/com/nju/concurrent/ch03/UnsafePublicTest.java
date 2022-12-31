package com.nju.concurrent.ch03;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @description 不正确的发布引起问题
 * @date:2022/12/17 15:53
 * @author: qyl
 */
public class UnsafePublicTest {
    public static Holder holder;

    /**
     * 非最新引用
     */
    @Test
    public void test1() throws InterruptedException {
        new Thread (this::initialize, "t1").start ( );
        // 始终无法获取到最新holder引用
        Thread t2 = new Thread (() -> {
            while (holder == null) {
            }
            Assertions.assertTrue (holder != null);
        }, "t2");
        t2.start ( );
        t2.join (1000);
    }

    /**
     * 最新引用 非最新状态
     */
    @Test
    public void test2() throws InterruptedException {
        initialize ( );
        Thread t1 = new Thread (() -> holder.assertSanity ( ), "t1");
        Thread t2 = new Thread (() -> holder.setN (5), "t2");
        t1.start ();
        t2.start ();
        t1.join ();
    }

    public void initialize() {
        try {
            TimeUnit.SECONDS.sleep (1);
        } catch (InterruptedException e) {
            throw new RuntimeException (e);
        }
        holder = new Holder (42);
    }
}

class Holder {
    private int n;

    public Holder(int n) {
        this.n = n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void assertSanity() {
        int temp = n;
        try {
            TimeUnit.SECONDS.sleep (1);
        } catch (InterruptedException e) {
            throw new RuntimeException (e);
        }
        if (temp != n) {
            throw new AssertionError ("this statement is false");
        }
    }
}
