package com.nju.concurrent.ch04;

import net.jcip.annotations.NotThreadSafe;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description 不变约束 lower <= upper
 * @date:2022/12/17 20:03
 * @author: qyl
 */
public class NumberRangeTest {
}

/**
 * 非安全 在偶然的情况下能够同时通过setLower 和 setUpper的检查 ，之后进行设置的lower 和  upper 可能已经违反约束
 */
@NotThreadSafe
class UnsafeNumberRange {
    private final AtomicInteger lower = new AtomicInteger (0);
    private final AtomicInteger upper = new AtomicInteger (0);

    public void setLower(int i) {
        // 不安全的检查再运行 可能其他线程这时候再设置upper
        if (i > upper.get ( )) {
            throw new IllegalArgumentException ("lower is bigger than upper");
        }
        lower.set (i);
    }

    public void setUpper(int i) {
        // 不安全的检查再运行 可能其他线程这时候在设置lower
        if (i < lower.get ( )) {
            throw new IllegalArgumentException ("lower is bigger than upper");
        }
        upper.set (i);
    }

    public boolean isInRange(int i) {
        return (i >= lower.get ( ) && i <= upper.get ( ));
    }
}

@ThreadSafe
class SafeNumberRange {
    private final AtomicInteger lower = new AtomicInteger (0);
    private final AtomicInteger upper = new AtomicInteger (0);

    public synchronized void setLower(int i) {
        // 不安全的检查再运行 可能其他线程这时候再设置upper
        if (i > upper.get ( )) {
            throw new IllegalArgumentException ("lower is bigger than upper");
        }
        lower.set (i);
    }

    public synchronized void setUpper(int i) {
        // 不安全的检查再运行 可能其他线程这时候在设置lower
        if (i < lower.get ( )) {
            throw new IllegalArgumentException ("lower is bigger than upper");
        }
        upper.set (i);
    }

    public boolean isInRange(int i) {
        return (i >= lower.get ( ) && i <= upper.get ( ));
    }
}
