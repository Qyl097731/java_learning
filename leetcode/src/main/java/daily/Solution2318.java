package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/12/14 11:43
 * @author: qyl
 */
public class Solution2318 {
    static int mod = (int) (1e9 + 7);
    static int Mn = (int) 1e4 + 1;
    static int[][][] dp = new int[Mn + 5][7][7];

    static {
        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 6; j++) {
                if (i != j && gcd (i, j) == 1) {
                    dp[2][i][j] = 1;
                }
            }
        }
        for (int i = 2; i < Mn; i++) {
            for (int j = 1; j <= 6; j++) {
                for (int k = 1; k <= 6; k++) {
                    if (j != k && gcd (j, k) == 1) {
                        for (int l = 1; l <= 6; l++) {
                            if (l != j) {
                                dp[i + 1][j][k] += dp[i][k][l];
                                dp[i + 1][j][k] %= mod;
                            }
                        }
                    }
                }
            }
        }
    }

    private static int gcd(int x, int y) {
        return y == 0 ? x : gcd (y, x % y);
    }

    public int distinctSequences(int n) {
        if (n == 1) {
            return 6;
        }
        int res = 0;
        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 6; j++) {
                res = (res + dp[n][i][j]) % mod;
            }
        }
        return res;
    }

    @Test
    public void test() {
        distinctSequences (20);
    }
}
