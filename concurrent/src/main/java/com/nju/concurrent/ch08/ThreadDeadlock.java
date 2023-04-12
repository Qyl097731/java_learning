package com.nju.concurrent.ch08;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @description 在单线程化的Executor中死锁的任务
 * @date:2022/12/24 17:49
 * @author: qyl
 */
public class ThreadDeadlock {
    ExecutorService exec = Executors.newSingleThreadExecutor ( );

    public class ReaderPageTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            Future<String> header, footer;
            header = exec.submit (new LoadFileTask ("header.html"));
            footer = exec.submit (new LoadFileTask ("footer.html"));
            // 由于是单线程 所以get阻塞等待子任务完成产生了死锁，因为子线程始终分不到CPU
            return header.get () + footer.get ();
        }
    }

    private class LoadFileTask implements Callable<String> {
        String msg;

        public LoadFileTask(String s) {
            this.msg = s;
        }

        @Override
        public String call() throws Exception {
            return "load " + msg;
        }
    }
}
