package chapter03.demo6;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description 线程池处理所有的命令行列出的文件和目录
 * @date:2022/10/24 9:33
 * @author: qyl
 */
public class GZipAllFiles {
    public final static int THREAD_COUNT = 4;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);

        for (String filename : args) {
            File f = new File(filename);
            if (f.exists()){
                if (f.isDirectory()){
                    File[] files = f.listFiles();
                    assert files != null;
                    for (File file : files) {
                        if (!file.isDirectory()){
                            submitGZipTask(pool, file);
                        }
                    }
                }else {
                    submitGZipTask(pool, f);
                }
            }
        }
        /**
         * 不会终止等待的工作，通知后续不会再添加任务了，当所有任务都完成，就关闭线程池。
         */
        pool.shutdown();
    }

    private static void submitGZipTask(ExecutorService pool, File f) {
        Runnable task = new GZipRunnable(f);
        pool.submit(task);
    }

}
