package daily;

/**
 * @description
 * @date:2023/1/16 21:33
 * @author: qyl
 */
public class Solution309 {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        if (n == 0) {
            return 0;
        }
        int[][] dp = new int[n][3];
        dp[0][0] = -prices[0];
        dp[0][1] = dp[0][2] = 0;
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max (dp[i - 1][0], dp[i - 1][2] - prices[i]);
            dp[i][1] = dp[i - 1][0] + prices[i];
            dp[i][2] = Math.max (dp[i - 1][2], dp[i - 1][1]);
        }
        return Math.max (dp[n - 1][0], Math.max (dp[n - 1][1], dp[n - 1][2]));
    }
}
