package chapter03;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * projectName:  juc
 * packageName: chapter03
 * date: 2022-07-26 21:42
 * author 邱依良
 */
public class CompletableFutureAPIDemo {
    public static void main(String[] args)  {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc";
        });
//        System.out.println(future.get());
        System.out.println(future.complete("completeValue")+"\t"+future.join());
    }
}
