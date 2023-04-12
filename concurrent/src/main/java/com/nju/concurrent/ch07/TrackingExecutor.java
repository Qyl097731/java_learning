package com.nju.concurrent.ch07;

import java.util.*;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @description 关闭之后，ExecutorService获取被取消的任务
 * @date:2022/12/23 15:09
 * @author: qyl
 */
public class TrackingExecutor extends AbstractExecutorService {
    private final ExecutorService exec;
    private final Set<Runnable> tasksCancelledAtShutdown = Collections.synchronizedSet (new HashSet<> ( ));

    public TrackingExecutor(ExecutorService exec) {
        this.exec = exec;
    }

    @Override
    public void shutdown() {
        exec.shutdown ();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return exec.shutdownNow ();
    }

    @Override
    public boolean isShutdown() {
        return exec.isShutdown ();
    }

    @Override
    public boolean isTerminated() {
        return exec.isTerminated ();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return exec.awaitTermination (timeout,unit);
    }

    @Override
    public void execute(Runnable command) {
        exec.execute (() -> {
            try {
                command.run ( );
            } finally {
                // 把取消的任务存入集合
                if (isShutdown ( ) && Thread.currentThread ( ).isInterrupted ( ))
                    tasksCancelledAtShutdown.add (command);
            }
        });
    }

    public List<Runnable> getCancelledTasks() {
        if (!exec.isTerminated ( )) {
            throw new IllegalStateException ( );
        }
        return new ArrayList<> (tasksCancelledAtShutdown);
    }
}
