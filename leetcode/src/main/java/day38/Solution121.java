package day38;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @description
 * @date:2022/11/1 18:42
 * @author: qyl
 */
public class Solution121 {
    int res = 0;

    public int maxProfit(int[] prices) {
        int n = prices.length;
        int minValue = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (minValue < prices[i]){
                res = Math.max(res, prices[i] - minValue);
            }
            minValue = Math.min(minValue,prices[i]);
        }
        return res;
    }

    @Test
    public void test() {
        System.out.println(maxProfit(new int[]{7,1,5,3,6,4}));
    }
}
