package com.nju.concurrent.ch03;

import java.util.List;
import java.util.concurrent.*;

/**
 * @description 64位数据多线程测试
 * @date:2022/12/14 21:10
 * @author: qyl
 */
public class Date64Test {
    static long num;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool (3);
        List<Callable<Void>> task = List.of (
                () -> {
                    num = 1234567891L;
                    return null;
                },
                () -> {
                    num = 9999999999L;
                    return null;
                },
                () -> {
                    System.out.println (num);
                    return null;
                }
        );
        List<Future<Void>> futures = pool.invokeAll (task);
        for (Future<Void> f : futures) {
            f.get ();
        }

        pool.shutdown ();
    }
}
