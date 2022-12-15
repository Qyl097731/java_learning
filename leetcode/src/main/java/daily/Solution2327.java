package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/12/15 23:38
 * @author: qyl
 */
public class Solution2327 {
    static int mod = (int) (1e9 + 7);

    public int peopleAwareOfSecret(int n, int delay, int forget) {
        long[] dp = new long[n + 1];
        for (int i = 1; i <= delay; i++) {
            dp[i] = 1;
        }
        for (int i = delay + 1; i <= n; i++) {
            if (i - forget >= 0) {
                for (int j = i - forget + 1; j <= i; j++) {
                    dp[j] -= dp[i - forget];
                    dp[j] %= mod;
                }
                dp[i - forget] = 0;
            }
            dp[i] = dp[i - delay] % mod + dp[i - 1] % mod;
            dp[i] = (mod + dp[i]) % mod;
        }
        return (int) ((dp[n] + mod) % mod);
    }

    @Test
    public void test() {
        peopleAwareOfSecret (6, 2, 4);
    }
}
