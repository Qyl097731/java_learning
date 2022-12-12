package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/12/12 11:34
 * @author: qyl
 */
public class Solution2312 {
    public long sellingWood(int m, int n, int[][] prices) {
        long[][] dp = new long[m + 1][n + 1];


        int[][] v = new int[205][205];
        for (int[] price : prices) {
            v[price[0]][price[1]] = price[2];
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = v[i][j];
                for (int k = 0; k <= j; k++) {
                    dp[i][j] = Math.max (dp[i][j], dp[i][j - k] + dp[i][k]);
                }
                for (int k = 0; k <= i; k++) {
                    dp[i][j] = Math.max (dp[i][j], dp[i - k][j] + dp[k][j]);
                }
            }
        }
        return dp[m][n];
    }

    @Test
    public void test() {
        sellingWood (3, 5, new int[][]{{1, 4, 2}, {2, 2, 7}, {2, 1, 3}});
    }
}
