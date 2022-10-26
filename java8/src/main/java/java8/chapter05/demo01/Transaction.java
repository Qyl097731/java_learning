package java8.chapter05.demo01;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description
 * @date:2022/10/26 22:23
 * @author: qyl
 */
@Getter
@AllArgsConstructor
public class Transaction {
    private final Trader trader;
    private final int year;
    private final int value;

    @Override
    public String toString() {
        return "{" + this.trader + ", " +
                "year: " + this.year + ", " +
                "value:" + this.value + "}";
    }
}
