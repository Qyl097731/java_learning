package com.nju.concurrent.ch07;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description 通过一个专门的线程终端任务
 * @date:2022/12/21 23:15
 * @author: qyl
 */
public class TimeRunTest1 {
    private static final ScheduledExecutorService cancelExec = new ScheduledThreadPoolExecutor (1);

    public static void timeRun(Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        class RethrowableTask implements Runnable {
            private volatile Throwable t;

            @Override
            public void run() {
                try {
                    r.run ( );
                } catch (Throwable t) {
                    this.t = t;
                }
            }

            void rethrow() {
                if (t != null) throw new RuntimeException ( );
            }
        }

        RethrowableTask task = new RethrowableTask ( );
        final Thread taskThread = new Thread (task);
        taskThread.start ( );
        cancelExec.schedule (taskThread::interrupt, timeout, unit);
        taskThread.join (unit.toMillis (timeout));
        task.rethrow ();
    }
}
