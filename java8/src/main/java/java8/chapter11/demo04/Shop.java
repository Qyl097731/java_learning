package java8.chapter11.demo04;

import java8.chapter11.demo03.Discount;
import java8.chapter11.demo03.Quote;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * @description
 * @date:2022/11/1 14:51
 * @author: qyl
 */
public class Shop {
    private static final Random random = new Random();

    private String name;

    public Shop(String name) {
        this.name = name;
    }

    public static void randomDelay() {
        int delay = 500 + random.nextInt(2000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPrice(String product) {
        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[
                random.nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", name, price, code);
    }

    private double calculatePrice(String product) {
        randomDelay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }
}
