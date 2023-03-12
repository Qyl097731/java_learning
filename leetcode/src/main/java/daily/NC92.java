package daily;

/**
 * @description
 * @date 2023/3/11 16:19
 * @author: qyl
 */
public class NC92 {
    public String LCS(String string1, String string2) {
        if (string1 == null || string2 == null || string1.isEmpty () || string2.isEmpty ()) {
            return "";
        }
        int n = string1.length (), m = string2.length ();
        int[][] dp = new int[n + 1][m + 1];
        char[] s1 = string1.toCharArray (), s2 = string2.toCharArray ();
        dp (s1, s2, n, m, dp);

        StringBuilder result = new StringBuilder ();
        for (int index = dp[n][m] - 1; index >= 0; ) {
            if (m > 0 && dp[n][m] == dp[n][m - 1]) {
                m--;
            } else if (n > 0 && dp[n][m] == dp[n - 1][m]) {
                n--;
            } else {
                result.insert (0, s1[n]);
                n--;
                m--;
            }
        }
        return result.toString ();
    }

    private void dp(char[] s1, char[] s2, int n, int m, int[][] dp) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                dp[i + 1][j + 1] = Math.max (dp[i][j + 1], dp[i + 1][j]);
                if (s1[i] == s2[j]) {
                    dp[i + 1][j + 1] = Math.max (dp[i + 1][j + 1], dp[i][j] + 1);
                }
            }
        }
    }
}
