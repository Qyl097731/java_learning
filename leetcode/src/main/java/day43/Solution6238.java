package day43;

/**
 * @description
 * @date:2022/11/12 22:28
 * @author: qyl
 */
public class Solution6238 {
    public int countGoodStrings(int low, int high, int zero, int one) {
        long[] dp = new long[high + 1];
        int mod = (int) 1e9 + 7;
        long res = 0;
        dp[zero] = 1;
        dp[one] += (zero == one ? dp[zero] : 1);
        for (int i = 0; i <= high; i++) {
            dp[i] += ((i - zero) >= 0 ? dp[i - zero] : 0) + ((i - one) >= 0 ? dp[i - one] : 0);
            dp[i] %= mod;
            if (low <= i) {
                res = (res + dp[i]) % mod;
            }
        }
        return (int) res;

    }
}
