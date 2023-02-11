package daily;

import java.util.Arrays;

/**
 * @description
 * @date 2023/2/11 11:32
 * @author: qyl
 */
public class Solution1223 {
    final int mod = (int) 1e9 + 7;
    int[] rollMax;
    int[][][] dp;

    public int dieSimulator(int n, int[] rollMax) {
//        return solve1(n,rollMax);
        return solve2 (n, rollMax);
    }

    private int solve2(int n, int[] rollMax) {
        int res = 0;
        this.rollMax = rollMax;
        this.dp = new int[n][7][16];
        for (int i = 1; i <= 6; i++) {
            Arrays.fill (dp[0][i], 1);
        }
        for (int left = 1; left < n; left++) {
            for (int last = 1; last <= 6; last++) {
                for (int count = 0; count <= rollMax[last - 1]; count++) {
                    for (int j = 1; j <= 6; j++) {
                        if (j == last && count > 0) {
                            dp[left][last][count] = (dp[left][last][count] + dp[left - 1][j][count - 1]) % mod;
                        }
                        if (j != last) {
                            dp[left][last][count] = (dp[left][last][count] + dp[left - 1][j][rollMax[j - 1] - 1]) % mod;
                        }
                    }
                }
            }
        }

        for (int i = 1; i <= 6; i++) {
            res = (res + dp[n - 1][i][rollMax[i - 1] - 1]) % mod;
        }
        return res;
    }

    private int solve1(int n, int[] rollMax) {
        int res = 0;
        this.rollMax = rollMax;
        this.dp = new int[n][7][16];
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= 6; j++) {
                Arrays.fill (dp[i][j], -1);
            }
        }
        for (int i = 1; i <= 6; i++) {
            res += dfs (n - 1, i, rollMax[i - 1] - 1);
            res %= mod;
        }
        return res;
    }

    private int dfs(int left, int last, int rollLeft) {
        if (dp[left][last][rollLeft] != -1) {
            return dp[left][last][rollLeft];
        }
        if (left == 0) {
            return 1;
        }
        // 当前是i
        int sum = 0;
        for (int i = 1; i <= 6; i++) {
            if (i == last && rollLeft > 0) {
                sum += dfs (left - 1, i, rollLeft - 1);
                sum %= mod;
            }
            if (i != last) {
                sum += dfs (left - 1, i, rollMax[i - 1] - 1);
                sum %= mod;
            }
        }
        dp[left][last][rollLeft] = sum;
        return sum;
    }
}
