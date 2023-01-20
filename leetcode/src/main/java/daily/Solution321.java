package daily;

/**
 * @description
 * @date:2023/1/20 19:35
 * @author: qyl
 */
public class Solution321 {
    public int maxCoins(int[] nums) {
        int n = nums.length;
        int[] val = new int[n + 2];
        val[0] = val[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            val[i + 1] = nums[i];
        }
        int[][] dp = new int[n + 2][n + 2];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 2; j <= n + 1; j++) {
                for (int k = i + 1; k <= j - 1; k++) {
                    dp[i][j] = Math.max (dp[i][j], dp[i + 1][k] + dp[k][j - 1] + val[k] * val[j] * val[i]);
                }
            }
        }
        return dp[0][n + 1];
    }
}
