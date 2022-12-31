package com.nju.concurrent.ch08;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @description 自定义的线程基类
 * @date:2022/12/25 19:02
 * @author: qyl
 */
public class MyAppThread extends Thread {
    public static final String DEFAULT_NAME = "MyAppThread";
    private static volatile boolean debugLifecycle = false;
    private static final AtomicInteger created = new AtomicInteger ( );
    private static final AtomicInteger alive = new AtomicInteger ( );
    private static final Logger log = Logger.getAnonymousLogger ( );

    public MyAppThread(Runnable r) {
        this (r, DEFAULT_NAME);
    }

    public MyAppThread(Runnable r, String name) {
        super (r,name+"-"+created.incrementAndGet ());
        setUncaughtExceptionHandler ((t, e) -> log.log (Level.SEVERE,"UNCAUGHT in thread " + t.getName () , e));
    }

    @Override
    public void run() {
        // 复制debug，确保它的值始终都一致。防止多线程之间修改之后后面缺少打印
        boolean debug = debugLifecycle;
        if (debug) log.log (Level.FINE,"Created " + getName ());
        try {
            alive.incrementAndGet ();
            super.run ();
        }finally {
            alive.decrementAndGet ();
            if (debug) log.log (Level.FINE,"Exiting " + getName ());
        }
    }

    public static int getThreadsCreated(){return created.get ();}
    public static int getThreadsAlive(){return alive.get ();}
    public static boolean getDebug(){return debugLifecycle;}
    public static void setDebug(boolean b){debugLifecycle = b;}
}

