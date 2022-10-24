package chapter04.demo02;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @description 处理文件读取，并且把解析任务放到线程池中
 * @date:2022/10/24 17:02
 * @author: qyl
 */
public class PooledWebLog {
    private final static int NUM_THREADS = 4;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
        Queue<LogEntry> results = new LinkedList<>();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])))) {
            for (String entry = in.readLine(); entry != null; entry = in.readLine()) {
                LookupTask task = new LookupTask(entry);
                Future<String> future = pool.submit(task);
                LogEntry logEntry = new LogEntry(entry, future);
                results.add(logEntry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (LogEntry result : results) {
            try {
                System.out.println(result.future.get());
            } catch (InterruptedException | ExecutionException e) {
                System.err.println(result.original);
            }
        }
        pool.shutdown();
    }
    private static class LogEntry{
        private final String original;
        private final Future<String> future;

        private LogEntry(String original, Future<String> future) {
            this.original = original;
            this.future = future;
        }
    }

}
