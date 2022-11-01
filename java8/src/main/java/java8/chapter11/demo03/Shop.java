package java8.chapter11.demo03;

import java.util.Random;

import static java8.chapter11.demo02.Shop.delay;

/**
 * @description
 * @date:2022/11/1 13:31
 * @author: qyl
 */
public class Shop {
    private String name;

    private Random random = new Random();

    public Shop(String name) {
        this.name = name;
    }

    public String getPrice(String product) {
        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[
                random.nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", name, price, code);
    }

    private double calculatePrice(String product) {
        delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }
}
