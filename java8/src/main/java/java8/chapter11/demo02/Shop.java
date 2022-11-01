package java8.chapter11.demo02;

import lombok.Getter;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * @description
 * @date:2022/11/1 11:42
 * @author: qyl
 */
@Getter
public class Shop {
    private String name;

    private Random random = new Random();

    public Shop(String name) {
        this.name = name;
    }

    /**
     * 异步方法
     * @param product
     * @return
     */
    public Future<Double> getPriceAsync(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        // 异步任务
        new Thread(() -> {
            // 当任务结束，就通过get把数值存在futurePrice
            try {
                double price = calculatePrice(product);
                futurePrice.complete(price);
            } catch (Exception ex) {
                futurePrice.completeExceptionally(ex);
            }
        }).start();
        // 无需等待还没结束的计算，直接返回Future对象
        return futurePrice;
        //        return CompletableFuture.supplyAsync(()->calculatePrice(product));
    }

    /**
     * 同步方法
     * @param product
     * @return
     */
    public double getPrice(String product) {
        return calculatePrice(product);
    }

    private double calculatePrice(String product) {
        delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    // 模拟查询对应商品的信息
    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
