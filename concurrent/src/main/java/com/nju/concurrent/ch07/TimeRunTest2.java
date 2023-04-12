package com.nju.concurrent.ch07;

import java.util.concurrent.*;

/**
 * @description 通过Future来取消任务
 * @date:2022/12/21 23:15
 * @author: qyl
 */
public class TimeRunTest2 {
    private static final ExecutorService taskExec = Executors.newFixedThreadPool (1);


    public static void timeRun(Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        Future<?> task = taskExec.submit (r);
        try {
            task.get (timeout,unit);
        } catch (ExecutionException e) {
            throw new RuntimeException (e);
        } catch (TimeoutException e) {
            // 任务取消
        } finally {
            // 如果任务结束，无害取消
            task.cancel (true);
        }
    }
}
