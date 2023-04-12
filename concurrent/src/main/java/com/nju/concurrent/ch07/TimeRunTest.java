package com.nju.concurrent.ch07;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description
 * @date:2022/12/21 23:15
 * @author: qyl
 */
public class TimeRunTest {
    private static final ScheduledExecutorService cancelExec = new ScheduledThreadPoolExecutor (1);

    public static void timeRun(Runnable r, long timeout, TimeUnit unit){
        final Thread taskThead = Thread.currentThread ();
        cancelExec.schedule (taskThead::interrupt,timeout,unit);
        r.run ();
    }
}
