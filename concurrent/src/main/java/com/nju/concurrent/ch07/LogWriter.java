package com.nju.concurrent.ch07;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @description LogWriter通过BlockingQueue把这个任务移交给日志线程，并由日志线程写入。是一个多生产者、单消费者的设计。
 * @date:2022/12/22 17:07
 * @author: qyl
 */
public class LogWriter {
    private final BlockingQueue<String>queue;
    private final LoggerThread logger;

    public LogWriter(Writer writer) {
        this.queue = new LinkedBlockingQueue<> (100);
        this.logger = new LoggerThread (writer);
    }

    public void start(){logger.start ();}

    public void log(String msg) throws InterruptedException {
        queue.put (msg);
    }

    private class LoggerThread extends Thread{

        private final PrintWriter writer;

        public LoggerThread(Writer writer) {
            this.writer = new PrintWriter (writer);
        }

        @Override
        public synchronized void start() {
            try {
                while (true) writer.println ( queue.take () );
            } catch (InterruptedException e) {
            }finally {
                writer.close ();
            }
        }
    }

}
