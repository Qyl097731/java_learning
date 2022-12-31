package com.nju.concurrent.ch07.demo02;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.*;

/**
 * @description 重写 newTaskFor，使得能创建自己的Future
 * @date:2022/12/22 16:52
 * @author: qyl
 */
@ThreadSafe
public class CancellingExecutor extends ThreadPoolExecutor {
    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super (corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        if (callable instanceof CancellableTask) {
            return ((CancellableTask<T>) callable).newTask ( );
        } else {
            return super.newTaskFor (callable);
        }
    }
}
