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
 * @date: 2022-07-26 23:13
 */
public class CompletableFutureWithThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(3);
        try {
            CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
                try{TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("1号任务\t" + Thread.currentThread().getName());
                return "abcd";
            },service).thenRun(() -> {
                try{TimeUnit.MICROSECONDS.sleep(1);} catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("2号任务\t" + Thread.currentThread().getName());
            }).thenRun(() -> {
                try{TimeUnit.MICROSECONDS.sleep(1);} catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("3号任务\t" + Thread.currentThread().getName());
            }).thenRun(() -> {
                try{TimeUnit.MICROSECONDS.sleep(1);} catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("4号任务\t" + Thread.currentThread().getName());
            });
            System.out.println(completableFuture.get(21, TimeUnit.SECONDS));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            service.shutdown();
        }
    }
}
