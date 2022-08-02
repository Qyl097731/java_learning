package chapter03;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * projectName:  juc
 * packageName: chapter03
 *
 * @author 邱依良
 * @date: 2022-07-26 23:47
 */
public class CompletableFutureCombineDemo {
    public static void main(String[] args) {
        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t----启动");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });

        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t----启动");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 20;
        });

        CompletableFuture<Integer> combine = completableFuture1.thenCombine(completableFuture2, (x, y) -> {
            System.out.println("开始合并结果");
            return x + y;
        });

        System.out.println(combine.join());
    }
}
