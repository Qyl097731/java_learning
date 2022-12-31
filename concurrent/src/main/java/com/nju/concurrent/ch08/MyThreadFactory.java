package com.nju.concurrent.ch08;

import java.util.concurrent.ThreadFactory;

/**
 * @description 定制线程工厂
 * @date:2022/12/25 18:45
 * @author: qyl
 */
public class MyThreadFactory implements ThreadFactory {
    private final String poolName;


    public MyThreadFactory(String poolName) {
        this.poolName = poolName;
    }


    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread (r, poolName);
        thread.setUncaughtExceptionHandler (Thread.getDefaultUncaughtExceptionHandler ());
        return thread;
    }
}
