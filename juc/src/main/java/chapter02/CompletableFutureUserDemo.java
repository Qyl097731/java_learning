package chapter02;

import java.util.concurrent.*;

/**
 * projectName:  juc
 * packageName: chapter02
 * date: 2022-07-25 22:57
 * author 邱依良
 */
public class CompletableFutureUserDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        try {
            CompletableFuture.supplyAsync(() -> {
                System.out.println(Thread.currentThread().getName() + "-----come in");
                int result = ThreadLocalRandom.current().nextInt(10);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(result > 1){
                    int i = result / 0;
                }
                return result;
            },threadPool).whenComplete((v, e) -> {
                if (e == null) {
                    System.out.println("-----计算完成" + v);
                }
            }).exceptionally(e -> {
                e.printStackTrace();
                System.out.println("异常处理====" + e.getMessage());
                return null;
            });
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
        System.out.println(Thread.currentThread().getName() + "线程执行其他任务了");
//        TimeUnit.SECONDS.sleep(5);

    }

    private static void future1() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            int result = ThreadLocalRandom.current().nextInt(10);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return result;
        });
        System.out.println(Thread.currentThread().getName() + "线程执行其他任务了");

        System.out.println(completableFuture.get());
    }
}
