package daily;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @description
 * @date:2023/1/18 15:17
 * @author: qyl
 */
public class Solution494 {
    public int findTargetSumWays(int[] nums, int target) {
//        return solve1(nums,target);
//        return solve2 (nums, target);
        return solve3 (nums, target);
    }

    private int solve3(int[] nums, int target) {
        int sum = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
        }
        int diff = sum - target;
        if (diff < 0 || (diff) % 2 == 1) {
            return 0;
        }
        int neg = diff / 2;
        int[] dp = new int[neg + 1];
        dp[0] = 1;
        for (int i = 0; i < n; i++) {
            for (int j = neg; j >= nums[i]; j--) {
                dp[j] = dp[j - nums[i]];
            }
        }
        return dp[neg];
    }

    private int solve2(int[] nums, int target) {
        int m = Arrays.stream (nums).sum ();
        if (m < target) {
            return 0;
        }
        // 防止 target 直接负数
        target += m;
        m = 2 * m + 1;
        int n = nums.length;
        int[][] dp = new int[n + 1][m];
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < m; j++) {
                if (j - nums[i - 1] >= 0) {
                    dp[i][j] += dp[i - 1][j - nums[i - 1]];
                }
                if (j + nums[i - 1] < m) {
                    dp[i][j] += dp[i - 1][j + nums[i - 1]];
                }
            }
        }
        return dp[n][target];
    }

    @Test
    public void test() {
        findTargetSumWays (new int[]{1}, 1);
    }

    private int solve1(int[] nums, int target) {
        return backtrack (nums, target, 0, 0, nums.length);
    }

    private int backtrack(int[] nums, int target, int sum, int index, int n) {
        if (index == n) {
            return target == sum ? 1 : 0;
        }
        return backtrack (nums, target, sum + nums[index], index + 1, n)
                + backtrack (nums, target, sum - nums[index], index + 1, n);
    }
}
