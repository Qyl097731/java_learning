package java8.chapter11.demo03;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static java.util.stream.Collectors.toList;

/**
 * @description
 * @date:2022/11/1 13:37
 * @author: qyl
 */
public class Client {

    List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
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

    public List<String> findPrices1(String product) {
        return shops.stream()
                .map(shop -> shop.getPrice(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(toList());
    }

    @Test
    public void test1() {
        long start = System.nanoTime();
        System.out.println(findPrices1("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    public List<String> findPrices2(String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                // thenCompose方法允许你对两个异步操作进行流水线，第一个future操作完成时，将其结果作为参数传递给第二个操作
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote),
                        executor)))
                .collect(toList());

        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(toList());
    }

    @Test
    public void test2() {
        long start = System.nanoTime();
        System.out.println(findPrices2("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }
}
