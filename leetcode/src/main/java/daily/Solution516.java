package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date 2023/2/8 18:06
 * @author: qyl
 */
public class Solution516 {
    public int longestPalindromeSubseq(String s) {
        int n = s.length ();
        int[][] dp = new int[n + 1][n + 1];
        for (int i = n - 1; i >= 0; i--) {
            dp[i][i] = 1;
            for (int j = i + 1; j < n; j++) {
                if (s.charAt (i) == s.charAt (j)) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max (dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[0][n - 1];
    }

    @Test
    public void test() {
        longestPalindromeSubseq ("bbbab");
    }
}
