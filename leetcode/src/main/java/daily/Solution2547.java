package daily;

/**
 * @description
 * @date:2023/1/23 23:04
 * @author: qyl
 */
public class Solution2547 {
    public int minCost(int[] nums, int k) {
//        return solve1(nums,k);
        return solve2 (nums, k);
    }

    private int solve2(int[] nums, int k) {
        int n = nums.length;
        int[] dp = new int[n + 1];
        for (int i = 0; i < n; i++) {
            int unique = 0;
            int mn = Integer.MAX_VALUE;
            int[] cost = new int[n];
            for (int j = i; j >= 0; j--) {
                int x = nums[j];
                cost[x]++;
                if (cost[x] == 1) {
                    unique++;
                } else if (cost[x] == 2) {
                    unique--;
                }
                mn = Math.min (mn, dp[j] - unique);
            }
            dp[i + 1] = k + mn;
        }
        return dp[n] + n;
    }

    private int solve1(int[] nums, int k) {
        int n = nums.length;
        int[] dp = new int[n + 1];
        for (int i = 0; i < n; i++) {
            dp[i + 1] = Integer.MAX_VALUE;
            int[] cost = new int[n];
            int t = 0;
            for (int j = i; j >= 0; j--) {
                int x = nums[j];
                cost[x]++;
                if (cost[x] == 2) {
                    t += 2;
                } else if (cost[x] > 2) {
                    t++;
                }
                dp[i + 1] = Math.min (dp[i + 1], t + dp[j] + k);
            }
        }
        return dp[n];
    }
}
