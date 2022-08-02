package chapter02;

import java.util.concurrent.*;

/**
 * projectName:  juc
 * packageName: chapter02
 * date: 2022-07-25 22:49
 * author 邱依良
 */
public class CompletableFutuerBuildDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(()->{
//            try {
//                System.out.println(Thread.currentThread().getName());
//                TimeUnit.SECONDS.sleep(1);
//            }catch (InterruptedException e){
//                e.printStackTrace();
//            }
//        });
//
//        System.out.println(completableFuture.get());

        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        },threadPool);
        System.out.println(stringCompletableFuture.get());
    }
}
