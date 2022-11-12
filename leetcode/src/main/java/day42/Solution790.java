package day42;

/**
 * @description
 * @date:2022/11/12 0:04
 * @author: qyl
 */
public class Solution790 {
    public int numTilings(int n) {
        int mod = (int)1e9+7;
        long[][] dp = new long[n+1][4];
        dp[0][3] = 1;
        for (int i = 1; i <= n; i++) {
            dp[i][0] = dp[i-1][3]%mod;
            dp[i][1] = (dp[i-1][2]+ dp[i-1][0])%mod;
            dp[i][2] = (dp[i-1][1]+ dp[i-1][0])%mod;
            dp[i][3] = (dp[i-1][1]+ dp[i-1][2]+dp[i-1][3]+dp[i-1][0])%mod;
        }

        return (int)dp[n][3];
    }
}
