package com.nju.concurrent.ch12;

/**
 * @description 基于关卡的计时器
 * @date:2023/1/2 21:35
 * @author: qyl
 */
public class BarrierTimer implements Runnable{
    private boolean started;
    private long startTime,endTime;

    public synchronized void run(){
        long t = System.nanoTime ();
        if (!started){
            started = true;
            startTime = t;
        }else {
            endTime = t;
        }
    }

    public synchronized void clear(){
        started = false;
    }
    public synchronized long getTime(){
        return endTime - startTime;
    }
}
