package chapter03;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * projectName:  juc
 * packageName: chapter03
 *
 * @author 邱依良
 * @date: 2022-07-26 23:44
 */
public class CompletableFutureFastDemo {
    public static void main(String[] args) {
        CompletableFuture<String> playA = CompletableFuture.supplyAsync(()->{
            System.out.println("A come in");
            try{
                TimeUnit.SECONDS.sleep(2);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return "playA";
        });
        CompletableFuture<String> playB = CompletableFuture.supplyAsync(()->{
            System.out.println("B come in");
            try{
                TimeUnit.SECONDS.sleep(3);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return "playB";
        });
        CompletableFuture<String> result = playA.applyToEither(playB, f -> {
            return f + " is winner";
        });
        System.out.println(Thread.currentThread().getName()+"\t"+result.join());
    }
}
