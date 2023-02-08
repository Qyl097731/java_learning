package daily;

/**
 * @description
 * @date 2023/2/8 17:16
 * @author: qyl
 */
public class Solution87 {
    public boolean isScramble(String s1, String s2) {
        int n = s1.length (), m = s2.length ();
        if (n != m) {
            return false;
        }
        boolean[][][] dp = new boolean[n][n][n + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                dp[i][j][1] = s1.charAt (i) == s2.charAt (j);
            }
        }
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len <= n; i++) {
                for (int j = 0; j + len <= n; j++) {
                    for (int k = 1; k < len; k++) {
                        dp[i][j][len] |= (dp[i][j][k] && dp[i + k][j + k][len - k]);
                        dp[i][j][len] |= (dp[i][j + len - k][k] && dp[i + k][j][len - k]);
                    }
                }
            }
        }
        return dp[0][0][n];
    }
}
