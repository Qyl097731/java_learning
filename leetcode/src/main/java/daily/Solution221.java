package daily;

/**
 * @description
 * @date:2023/1/14 17:54
 * @author: qyl
 */
public class Solution221 {
    public int maximalSquare(char[][] matrix) {
        int res = 0;
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return res;
        }
        int n = matrix.length, m = matrix[0].length;
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (matrix[i - 1][j - 1] == '1') {
                    dp[i][j] = Math.min (Math.min (dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                    res = Math.max (dp[i][j] * dp[i][j], res);
                }
            }
        }
        return res;
    }
}
