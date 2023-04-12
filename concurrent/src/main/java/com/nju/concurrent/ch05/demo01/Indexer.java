package com.nju.concurrent.ch05.demo01;

import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.zip.*;

/**
 * @description 归档
 * @date:2022/12/18 22:21
 * @author: qyl
 */
public class Indexer implements Runnable {
    private final BlockingQueue<File> queue;

    public Indexer(BlockingQueue<File> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                indexFile (queue.take ( ));
            }
        } catch (InterruptedException e) {
            Thread.currentThread ( ).interrupt ( );
        }
    }

    private void indexFile(File file) {
        try (DeflaterInputStream input = new DeflaterInputStream (new FileInputStream (file));
             DeflaterOutputStream output = new  DeflaterOutputStream(new FileOutputStream("./test.zip"))) {
            byte[] bytes = new byte[1024];
            while (input.read (bytes) != -1){
                output.write (bytes);
            }
        } catch (IOException e) {
            throw new RuntimeException (e);
        }
    }
}
