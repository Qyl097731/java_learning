package daily;

/**
 * @description
 * @date:2023/1/30 23:39
 * @author: qyl
 */
public class Solution122 {
    public int maxProfit(int[] p) {
        int n = p.length;
        int[][] dp = new int[n + 1][2];
        int res = 0;
        dp[1][0] = 0;
        dp[1][1] = -p[0];
        for (int i = 1; i < n; i++) {
            dp[i + 1][0] = Math.max (dp[i][1] + p[i], dp[i][0]);
            dp[i + 1][1] = Math.max (dp[i][0] - p[i], dp[i][1]);
            res = Math.max (dp[i + 1][0], Math.max (res, dp[i + 1][1]));
        }

        return res;
    }
}
