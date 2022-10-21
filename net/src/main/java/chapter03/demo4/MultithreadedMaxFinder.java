package chapter03.demo4;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @description 多线程查找数组中给定区间的最大值
 * @date:2022/10/21 23:24
 * @author: qyl
 */
public class MultithreadedMaxFinder {
    public static int max(int[] data) throws ExecutionException, InterruptedException {
        if (data.length == 1) {
            return data[0];
        } else if (data.length == 0) {
            throw new IllegalArgumentException();
        }

        // 将任务分解为两部分
        FindMaxTask task1 = new FindMaxTask(data, 0, data.length / 2);
        FindMaxTask task2 = new FindMaxTask(data, data.length / 2, data.length);

        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<Integer> future1 = service.submit(task1);
        Future<Integer> future2 = service.submit(task2);
        return Math.max(future1.get(),future2.get());
    }
}
