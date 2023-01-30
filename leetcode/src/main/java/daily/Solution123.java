package daily;

/**
 * @description
 * @date:2023/1/30 23:55
 * @author: qyl
 */
public class Solution123 {
    public int maxProfit(int[] p) {
        int n = p.length;
        int[][][] dp = new int[n + 1][2][3];
        dp[1][0][0] = 0;
        dp[1][1][0] = -p[0];
        for (int i = 1; i < n; i++) {
            // 交易两次
            dp[i + 1][0][2] = Math.max (dp[i][1][1] + p[i], dp[i][0][2]);
            dp[i + 1][1][2] = Math.max (dp[i][0][2] - p[i], dp[i][1][2]);

            // 交易一次
            dp[i + 1][0][1] = Math.max (dp[i][1][0] + p[i], dp[i][0][1]);
            dp[i + 1][1][1] = Math.max (dp[i][0][1] - p[i], dp[i][1][1]);

            // 没有交易
            dp[i + 1][0][0] = dp[i][0][0];
            dp[i + 1][1][0] = Math.max (dp[i][1][0], -p[i]);
        }
        return Math.max (dp[n][0][0], Math.max (dp[n][0][1], dp[n][0][2]));
    }
}
