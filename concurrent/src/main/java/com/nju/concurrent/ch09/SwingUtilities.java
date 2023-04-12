package com.nju.concurrent.ch09;

import java.util.concurrent.*;

/**
 * @description 使用Executor实现的SwingUtilities
 * @date:2022/12/26 18:35
 * @author: qyl
 */
public class SwingUtilities {
    private static final ExecutorService exec = Executors.newSingleThreadExecutor (new SwingThreadFactory());
    private static volatile Thread swingThread;

    private static class SwingThreadFactory implements ThreadFactory{

        @Override
        public Thread newThread(Runnable r) {
            swingThread = new Thread (r);
            return swingThread;
        }
    }

    public static boolean isEventDispatchThread(){
        return Thread.currentThread () == swingThread;
    }

    public static void invokeLater(Runnable task){
        exec.execute (task);
    }

    public static void invokeAndWait(Runnable task){
        Future<?> future = exec.submit (task);
        try {
            future.get ();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException (e);
        }
    }
}
