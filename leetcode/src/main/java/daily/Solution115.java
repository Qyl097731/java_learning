package daily;

/**
 * @description
 * @date:2023/1/27 23:44
 * @author: qyl
 */
public class Solution115 {
    public int numDistinct(String s, String t) {
        int n = s.length (), m = t.length ();
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (t.charAt (j) == s.charAt (i)) {
                    dp[i + 1][j + 1] = dp[i][j] + dp[i][j + 1];
                } else {
                    dp[i + 1][j + 1] = dp[i][j + 1];
                }
            }
        }
        return dp[n][m];
    }
}
