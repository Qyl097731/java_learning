package day50;

import java.util.stream.IntStream;

/**
 * @description
 * @date:2022/11/23 0:22
 * @author: qyl
 */
public class Solution1742 {
    public int countBalls(int lowLimit, int highLimit) {
        int res = 0;
        int[] cnt = IntStream.generate (() -> 0).limit (100).toArray ( );

        while (lowLimit <= highLimit) {
            res = Math.max (res, ++cnt[cal (lowLimit++)]);
        }
        return res;
    }

    private int cal(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }
}
