package daily;

/**
 * @description
 * @date:2022/12/14 12:36
 * @author: qyl
 */
public class Solution2320 {
    static int Mn = (int) (1e4 + 6);
    static long[] dp = new long[Mn];

    static int mod = (int) (1e9 + 7);

    static {
        dp[0] = 1;
        dp[1] = 2;
        for (int i = 2; i < Mn; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
            dp[i] %= mod;
        }
    }

    public int countHousePlacements(int n) {
        return (int) ((dp[n] * dp[n]) % mod);
    }
}
