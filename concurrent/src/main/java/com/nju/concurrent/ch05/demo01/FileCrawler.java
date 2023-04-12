package com.nju.concurrent.ch05.demo01;

import org.junit.platform.commons.util.CollectionUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;

/**
 * @description 磁盘扫描
 * @date:2022/12/18 22:16
 * @author: qyl
 */
public class FileCrawler implements Runnable{
    private final BlockingQueue<File> fileQueue;
    private final FileFilter fileFilter;
    private final File root;

    public FileCrawler(BlockingQueue<File> fileQueue, FileFilter fileFilter, File root) {
        this.fileQueue = fileQueue;
        this.fileFilter = fileFilter;
        this.root = root;
    }

    @Override
    public void run() {
        try {
            crawl(root);
        } catch (InterruptedException e) {
            throw new RuntimeException (e);
        }
    }

    private void crawl(File root) throws InterruptedException {
        File[] entries = root.listFiles ( );
        if (entries != null){
            for (File entry : entries) {
                if (entry.isDirectory ()){
                    crawl (entry);
                }else if (!alreadyIndexed(entry)){
                    fileQueue.put (entry);
                }
            }
        }
    }

    private boolean alreadyIndexed(File entry) {
//        return fileQueue.contains (entry);
        return false;
    }
}
