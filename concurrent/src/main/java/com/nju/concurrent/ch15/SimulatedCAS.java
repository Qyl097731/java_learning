package com.nju.concurrent.ch15;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * @description 模拟CAS
 * @date:2023/1/9 20:07
 * @author: qyl
 */
@ThreadSafe
public class SimulatedCAS {
    @GuardedBy ("this") private int value;

    public synchronized int get(){return value;}

    public synchronized int compareAndSwap(int expectedValue,int newValue){
        int oldValue = value;
        if (oldValue == expectedValue){
            value= newValue;
        }
        return oldValue;
    }

    public synchronized boolean compareAndSet(int expectedValue,int newValue){
        return (expectedValue == compareAndSwap (expectedValue,newValue));
    }
}
