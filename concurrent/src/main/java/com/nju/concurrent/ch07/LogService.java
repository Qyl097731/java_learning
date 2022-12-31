package com.nju.concurrent.ch07;

import net.jcip.annotations.GuardedBy;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @description 向LogWriter添加可靠的取消
 * @date:2022/12/23 10:44
 * @author: qyl
 */
public class LogService {
    private final BlockingQueue<String> queue;
    private final LoggerThread loggerThread;
    private final PrintWriter writer;

    @GuardedBy("this")
    private boolean isShutdown;
    @GuardedBy("this")
    private int reservations;

    public LogService(PrintWriter writer) {
        this.queue = new LinkedBlockingQueue<> (100);
        this.loggerThread = new LoggerThread ( );
        this.writer = writer;
    }

    public void start() {
        Runtime.getRuntime ().addShutdownHook (new Thread (LogService.this::stop));
        loggerThread.start ( );
    }

    public void stop() {
        synchronized (this) {
            isShutdown = true;
        }
        loggerThread.interrupt ( );
    }

    public void log(String msg) throws InterruptedException {
        synchronized (this) {
            if (isShutdown) throw new IllegalArgumentException ( );
            ++reservations;
        }
        queue.put (msg);
    }

    private class LoggerThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    try {
                        synchronized (LogService.this) {
                            if (isShutdown && reservations == 0) break;
                        }
                        String msg = queue.take ( );
                        synchronized (LogService.this) {
                            --reservations;
                        }
                        writer.println (msg);
                    } catch (InterruptedException e) {
                        throw new RuntimeException (e);
                    }
                }
            } finally {
                writer.close ( );
            }
        }
    }
}
