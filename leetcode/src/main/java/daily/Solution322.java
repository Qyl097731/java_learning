package daily;

/**
 * @description
 * @date:2023/1/17 20:58
 * @author: qyl
 */
public class Solution322 {
    public int coinChange(int[] coins, int amount) {
        final int max = Integer.MAX_VALUE;
        int n = coins.length;
        int[] dp = new int[amount + 1];
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            dp[i] = max;
            for (int j = 0; j < n; j++) {
                if (coins[i] <= i) {
                    dp[i] = Math.min (dp[i], dp[i - coins[j]] + 1);
                }
            }
        }
        return dp[amount] == max ? -1 : dp[amount];
    }
}
