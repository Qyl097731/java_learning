package com.nju.concurrent.ch14;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description 有限缓存使用显式的条件变量
 * @date:2023/1/3 22:49
 * @author: qyl
 */
@ThreadSafe
public class ConditionBoundedBuffer<T> {
    protected final Lock lock = new ReentrantLock ();
    // 条件谓词
    private final Condition notFull = lock.newCondition ();
    private final Condition notEmpty = lock.newCondition ();
    @GuardedBy ("lock")
    private final T[] items = (T[]) new Object[1024];
    @GuardedBy ("lock")
    private int tail ,head,count;

    public void put(T x) throws InterruptedException {
        lock.lock ();
        try {
            while (count == items.length){
                notFull.await ();
            }
            items[tail] = x;
            if (++tail == items.length){
                tail = 0;
            }
            ++count;
            notEmpty.signal ();
        }finally {
            lock.unlock ();
        }
    }

    public T take() throws InterruptedException {
        lock.lock ();
        try{
            while (count == 0){
                notEmpty.await ();
            }
            T x = items[head];
            if (++head == items.length){
                head = 0;
            }
            --count;
            notFull.signal ();
            return x;
        }finally {
            lock.unlock ();
        }
    }
}
