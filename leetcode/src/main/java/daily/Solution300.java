package daily;


/**
 * @description
 * @date:2022/12/10 18:55
 * @author: qyl
 */
public class Solution300 {
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n + 1];
        int res = 0;
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < i; j++) {
//                if (nums[j] < nums[i]) {
//                    dp[i] = Math.max (dp[i], dp[j] + 1);
//                }
//            }
//            res = Math.max (res,dp[i]);
//        }
        int tot = 0;
        for (int i = 0; i < n; i++) {
            int len = find (tot, dp, nums[i]);
            dp[len + 1] = nums[i];
            tot = Math.max (tot, len + 1);
        }
        return tot;
    }

    private int find(int len, int[] dp, int num) {
        int l = 0, r = len, mid;
        int res = 0;
        while (l <= r) {
            mid = (l + r + 1) / 2;
            if (dp[mid] < num) {
                res = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return res;
    }
}
