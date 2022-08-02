package chapter03;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * projectName:  juc
 * packageName: chapter03
 *
 * @author 邱依良
 * @date: 2022-07-26 21:49
 */
public class CompletableFutureAPI2Demo {
    public static void main(String[] args) {
        ExecutorService servicePool = Executors.newFixedThreadPool(2);
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }, servicePool).handle((f, e) -> {          //类似于finally
//                .thenApply(f -> {
            System.out.println("1111");
            return f + 1;
        }).handle((f, e) -> {
//                .thenApply(f -> {
            System.out.println("2222");
            return f + 1;
        }).whenComplete((v, e) -> {
            if (e == null) {
                System.out.println("计算结果为 " + v);
            }
        }).exceptionally(e -> {                 //类似于try/catch
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        });

        System.out.println(Thread.currentThread().getName() + "去忙其他任务了");
    }
}
