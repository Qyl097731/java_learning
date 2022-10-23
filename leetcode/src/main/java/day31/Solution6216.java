package day31;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @description
 * @date:2022/10/23 22:54
 * @author: qyl
 */
public class Solution6216 {
    public long minCost(int[] nums, int[] cost) {
        long res = Long.MAX_VALUE;
        int n = nums.length;
        long[] sumOfAb = new long[n + 1], sumOfB = new long[n + 1];
        long[][] numsAndCost = new long[n][2];
        for (int i = 0; i < n; i++) {
            numsAndCost[i] = new long[]{nums[i], cost[i]};
        }
        Arrays.sort(numsAndCost, Comparator.comparing(a -> a[0]));
        for (int i = 1; i <= n; i++) {
            sumOfAb[i] = sumOfAb[i - 1] + numsAndCost[i - 1][0] * numsAndCost[i - 1][1];
            sumOfB[i] = sumOfB[i - 1] + numsAndCost[i - 1][1];
        }
        for (int i = 1; i <= n; i++) {
            res = Math.min(res,
                    sumOfB[i] * numsAndCost[i - 1][0] - sumOfAb[i] + (sumOfAb[n] - sumOfAb[i]) - (sumOfB[n] - sumOfB[i]) * numsAndCost[i - 1][0]);
        }
        return res;
    }
}
