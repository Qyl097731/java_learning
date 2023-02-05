package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date 2023/2/5 23:19
 * @author: qyl
 */
public class Solution6346 {
    int n;
    int[] dp;
    int[] nums;
    int k;

    public int minCapability(int[] nums, int k) {
        n = nums.length;
        int l = 0, r = (int) 1e9 + 5, mid = 0, res = 0;
        this.k = k;
        this.nums = nums;
        dp = new int[n + 1];
        while (l <= r) {
            mid = (l + r) / 2;
            if (check (mid)) {
                r = mid - 1;
                res = mid;
            } else {
                l = mid + 1;
            }
        }
        return res;
    }

    private boolean check(int mid) {
        dp[0] = 0;
        dp[1] = nums[0] > mid ? 0 : 1;
        for (int i = 1; i < n; i++) {
            if (nums[i] > mid) {
                dp[i + 1] = dp[i];
            } else {
                dp[i + 1] = Math.max (dp[i - 1] + 1, dp[i]);
            }
        }
        return dp[n] >= k;
    }

    @Test
    public void test() {
        minCapability (new int[]{2, 3, 5, 9}, 2);
    }
}
