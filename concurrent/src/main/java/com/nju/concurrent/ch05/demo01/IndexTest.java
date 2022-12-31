package com.nju.concurrent.ch05.demo01;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.zip.*;

/**
 * @description 测试
 * @date:2022/12/18 22:26
 * @author: qyl
 */
public class IndexTest {
    public static void startIndexing(File[] roots) {
        BlockingQueue<File> queue = new LinkedBlockingQueue<> (10);
        FileFilter filter = pathname -> true;
        for (File root : roots) {
            new Thread (new FileCrawler (queue, filter, root)).start ( );
        }
        for (int i = 0; i < 5; i++) {
            new Thread (new Indexer (queue)).start ( );
        }
    }

    public static void main(String[] args) {
        startIndexing (new File[]{new File ("../")});
    }

    @Test
    public void test() {
        File file = new File ("D:/test.zip");
        try {
            file.createNewFile ( );
            ZipEntry entry = new ZipEntry ("1671290597512.jpg");
            try (BufferedInputStream input = new BufferedInputStream(new
                    FileInputStream("D:\\java_learning\\concurrent\\images\\1671290597512.jpg"))
                 ; ZipOutputStream output = new ZipOutputStream (new FileOutputStream (file))) {
                output.putNextEntry(entry);

                byte[] buffer = new byte[1024];
                int count = -1;
                while ((count = input.read(buffer)) != -1) {
                    output.write(buffer, 0, count);

                }
            } catch (IOException e) {
                throw new RuntimeException (e);
            }
        } catch (IOException e) {
            throw new RuntimeException (e);
        }
    }
}
