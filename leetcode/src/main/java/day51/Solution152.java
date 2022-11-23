package day51;

/**
 * @description
 * @date:2022/11/23 9:39
 * @author: qyl
 */
public class Solution152 {
    public int maxProduct(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[2][n];
        int res = Integer.MIN_VALUE;
        System.arraycopy (nums, 0, dp[0], 0, n);
        System.arraycopy (nums, 0, dp[1], 0, n);
        for (int i = 1; i < n; i++) {
            dp[0][i] = Math.max (nums[i], Math.max (dp[0][i - 1] * nums[i], dp[1][i - 1] * nums[i]));
            dp[1][i] = Math.min (nums[i], Math.min (dp[0][i - 1] * nums[i], dp[1][i - 1] * nums[i]));
            res = Math.max (dp[i][0], res);
        }
        for (int i = 0; i < n; i++) {
            res = Math.max (res, dp[0][i]);
        }
        return res;
    }
}
