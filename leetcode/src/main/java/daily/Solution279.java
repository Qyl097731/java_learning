package daily;

import java.util.Arrays;

/**
 * @description
 * @date:2023/1/15 22:39
 * @author: qyl
 */
public class Solution279 {
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill (dp, n);
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j * j < i; j++) {
                dp[i] = Math.min (dp[i], dp[i - j * j] + 1);
            }
        }
        return dp[n];
    }
}
