package chapter4;

import java.math.BigDecimal;

/**
 * @description
 * @date:2022/8/21 19:38
 * @author: qyl
 */
public class FinalTest {
    public static void main(String[] args) {
        BigDecimal bigDecimal = new BigDecimal("10.0");
        BigDecimal negate = bigDecimal.negate();
        System.out.println(bigDecimal.compareTo(negate.multiply(BigDecimal.valueOf(-1))));
    }
}
