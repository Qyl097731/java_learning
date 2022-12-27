package daily;

/**
 * @description
 * @date:2022/12/27 20:40
 * @author: qyl
 */
public class Offer10II {
    final int mod = (int) 1e9 + 7;

    public int numWays(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = (dp[i - 1] + dp[i - 2]) % mod;
        }
        return dp[n];
    }
}
