package chapter03;

import java.util.concurrent.CompletableFuture;

/**
 * projectName:  juc
 * packageName: chapter03
 *
 * @author 邱依良
 * @date: 2022-07-26 22:18
 */
public class CompletableFutureAPI3Demo {
    public static void main(String[] args) {
//        CompletableFuture.supplyAsync(()->{
//            return 1;
//        }).thenApply(f->{
//            return f+2;
//        }).thenApply(f ->{
//            return f+3;
//        }).thenAccept(System.out::println);

        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenRun(() -> {
        }).join());
        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenAccept(System.out::println).join());
        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenApply(r -> r + "resultB").join());

    }
}
