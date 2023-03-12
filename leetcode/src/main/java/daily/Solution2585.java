package daily;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @description
 * @date 2023/3/12 15:42
 * @author: qyl
 */
public class Solution2585 {
    private static final int MOD = (int) 1e9 + 7;

    public int waysToReachTarget(int target, int[][] types) {

        int n = types.length;
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int[] type : types) {
            int count = type[0], mark = type[1];
            for (int i = target; i >= 1; i--) {
                for (int j = 1; j <= count && j <= i / mark; j++) {
                    dp[i] += dp[i - j * mark];
                    dp[i] %= MOD;
                }
            }
        }
        return dp[target];
    }

    @Test
    public void test() {
        Assertions.assertEquals (waysToReachTarget (6, new int[][]{{6, 1}, {3, 2}, {2, 3}}), 7);
    }
}
