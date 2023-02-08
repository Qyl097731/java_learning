package daily;

/**
 * @description
 * @date 2023/2/8 17:46
 * @author: qyl
 */
public class Solution5 {
    public String longestPalindrome(String s) {
        int n = s.length ();
        boolean[][] dp = new boolean[n + 1][n + 1];
        int start = 0, mxLen = 1;
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }
        for (int len = 2; len < n; len++) {
            for (int i = 0; i + len <= n; i++) {
                int j = i + len - 1;
                dp[i][j] = s.charAt (i) == s.charAt (j);
                if (i + 1 <= j - 1) {
                    dp[i][j] &= dp[i + 1][j - 1];
                }
                if (dp[i][j] && len > mxLen) {
                    mxLen = len;
                    start = i;
                }
            }
        }
        return s.substring (start, start + mxLen);
    }
}
