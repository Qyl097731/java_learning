package com.nju.concurrent.ch08.demo01;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.CountDownLatch;

/**
 * @description ConcurrentPuzzleSolver 使用可携带结果的闭锁
 * @date:2022/12/26 17:16
 * @author: qyl
 */
@ThreadSafe
public class ValueLatch<T>{
    @GuardedBy ("this") private T value = null;
    private final CountDownLatch done = new CountDownLatch (1);

    public boolean isSet(){
        return done.getCount () == 0;
    }

    public synchronized void setValue(T newValue){
        if (!isSet ()){
            value = newValue;
            done.countDown ();
        }
    }

    public T getValue() throws InterruptedException {
        done.await ();
        synchronized (this){
            return value;
        }
    }
}
