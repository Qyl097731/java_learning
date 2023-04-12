package com.nju.concurrent.ch14;

import net.jcip.annotations.GuardedBy;

/**
 * @description 使用wait和notifyAll实现可重关闭的阀门
 * @date:2023/1/3 22:24
 * @author: qyl
 */
public class ThreadGate {
    @GuardedBy ("this") private boolean isOpen;
    @GuardedBy ("this") private int generation;

    public synchronized void close(){
        isOpen = false;
    }

    public synchronized void open(){
        ++generation;
        isOpen = true;
        notifyAll ();
    }

    // 阻塞 直到generation变化
    public synchronized void await() throws InterruptedException {
        int arrivalGeneration = generation;
        while (!isOpen && arrivalGeneration == generation){
            wait ();
        }
    }

}

