package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date 2023/3/12 22:43
 * @author: qyl
 */
public class Offer14 {
    int[] dp;

    public int cuttingRope(int n) {
        dp = new int[1 + n];
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                dp[i] = Math.max (dp[i], Math.max (j * (i - j), j * dp[i - j]));
            }
        }
        return dp[n];
    }

    private int dfs(int left) {
        if (dp[left] != -1) {
            return dp[left];
        }
        if (left != dp.length - 1) {
            dp[left] = left;
        } else {
            dp[left] = 1;
        }
        for (int i = 1; i < left; i++) {
            dp[left] = Math.max (dp[left], i * dfs (left - i));
        }
        return dp[left];
    }

    @Test
    public void test() {
        cuttingRope (3);
    }
}
