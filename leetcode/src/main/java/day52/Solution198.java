package day52;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/11/24 10:42
 * @author: qyl
 */
public class Solution198 {
    public int rob(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        if (n == 1) {
            return dp[0];
        }
        dp[1] = Math.max (dp[0], nums[1]);
        for (int i = 2; i < n; i++) {
            dp[i] = Math.max (dp[i - 1], dp[i - 2] + nums[i]);
        }
        return dp[n - 1];
    }

    @Test
    public void test() {
        System.out.println (rob (new int[]{2, 1, 1, 2}));
    }
}
