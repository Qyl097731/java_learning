package com.nju.concurrent.ch08;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;

/**
 * @description 使用Semaphore来遏制任务的提交
 * @date:2022/12/25 18:33
 * @author: qyl
 */
@ThreadSafe
public class BoundedExecutor {
    private final Executor exec;
    private final Semaphore semaphore;

    public BoundedExecutor(Executor exec, int bound) {
        this.exec = exec;
        this.semaphore = new Semaphore (bound);
    }

    public void submitTask(final  Runnable command) throws InterruptedException {
        semaphore.acquire ();
        try {
            exec.execute (()->{
                try {
                    command.run ();
                }finally {
                    semaphore.release ();
                }
            });
        }catch (RejectedExecutionException e){
            semaphore.release ( );
        }
    }
}
