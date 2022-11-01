package java8.chapter11.demo03;


import java.math.BigDecimal;

import static java8.chapter11.demo02.Shop.delay;

/**
 * @description
 * @date:2022/11/1 13:30
 * @author: qyl
 */
public class Discount {

    public enum Code {
        NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);
        private final int percentage;
        Code(int percentage) {
            this.percentage = percentage;
        }
    }

    public static String applyDiscount(Quote quote) {
        return quote.getShopName() + " price is " +
                Discount.apply(quote.getPrice(),
                        quote.getDiscountCode());
    }

    private static double apply(double price, Code code) {
        delay();
        return format(price * (100 - code.percentage) / 100);
    }

    private static double format(double v) {
        return BigDecimal.valueOf(v).setScale(2,BigDecimal.ROUND_UP).doubleValue();
    }
}
