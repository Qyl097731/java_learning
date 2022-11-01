package java8.chapter11.demo02;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @date:2022/11/1 11:41
 * @author: qyl
 */
public class CompleteFutureTest {
    List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"));


    /**
     * 同步
     */
    @Test
    public void test1() {
        long start = System.nanoTime();
        System.out.println(findPrices1("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    public List<String> findPrices1(String product) {
        return shops.stream()
                .map(shop -> String.format("%s price is %.2f",
                        shop.getName(), shop.getPrice(product)))
                .collect(toList());
    }

    @Test
    public void test2() {
        long start = System.nanoTime();
        System.out.println(findPrices2("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    public List<String> findPrices2(String product) {
        return shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f",
                        shop.getName(), shop.getPrice(product)))
                .collect(toList());
    }

    @Test
    public void test3() {
        long start = System.nanoTime();
        System.out.println(findPrices("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    public List<String> findPrices(String product) {
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(
                                () -> shop.getName() + " price is " +
                                        shop.getPrice(product)))
                        .collect(Collectors.toList());
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(toList());
    }

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

    @Test
    public void test4() {
        long start = System.nanoTime();
        System.out.println(findPrices4("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    public List<String> findPrices4(String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getName() + " price is " +
                        shop.getPrice(product), executor))
                .collect(Collectors.toList());
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(toList());
    }
}
