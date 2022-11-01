package java8.chapter11.demo04;

import java8.chapter11.demo03.Discount;
import java8.chapter11.demo03.Quote;
import java8.chapter11.demo03.Shop;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/**
 * @description
 * @date:2022/11/1 14:55
 * @author: qyl
 */
public class Client {
    List<Shop> shops = Arrays.asList(new java8.chapter11.demo03.Shop("BestPrice"),
            new java8.chapter11.demo03.Shop("LetsSaveBig"),
            new java8.chapter11.demo03.Shop("MyFavoriteShop"),
            new Shop("BuyItAll"));

    /**
     * 定制的执行器
     * 创建的是一个由守护线程构成的线程池
     */
    private final Executor executor =
            Executors.newFixedThreadPool(Math.min(shops.size(), 100),
                    r -> {
                        Thread t = new Thread(r);
                        // 使用守护线程——这种方式不会阻止程序的关停，如果将线程标记为守护进程，意味着程序退出时它也会被回收。
                        t.setDaemon(true);
                        return t;
                    });


    public Stream<CompletableFuture<String>> findPricesStream(String product) {
        return shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> shop.getPrice(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote ->
                        CompletableFuture.supplyAsync(
                                () -> Discount.applyDiscount(quote), executor)));
    }

    @Test
    public void test1(){
        findPricesStream("myPhone").map(f-> f.thenAccept(System.out::println));
    }

    @Test
    public void test2(){
        long start = System.nanoTime();
        CompletableFuture[] futures = findPricesStream("myPhone27S")
                .map(f -> f.thenAccept(
                        s -> System.out.println(s + " (done in " +
                                ((System.nanoTime() - start) / 1_000_000) + " msecs)")))
                .toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();
        System.out.println("All shops have now responded in "
                + ((System.nanoTime() - start) / 1_000_000) + " msecs");
    }
}
