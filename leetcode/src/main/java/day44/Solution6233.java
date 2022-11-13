package day44;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/11/13 10:27
 * @author: qyl
 */
public class Solution6233 {
    public double[] convertTemperature(double celsius) {
        return new double[]{celsius + 273.15, celsius * 1.80 + 32.00};
    }

    @Test
    public void test() {

    }
}
