package daily;

/**
 * @description
 * @date 2023/2/16 23:57
 * @author: qyl
 */
public class Solution209 {
    public int minSubArrayLen(int target, int[] nums) {
//        return solve1(target,nums);
        return solve2 (target, nums);
    }

    private int solve2(int target, int[] nums) {
        int n = nums.length;
        int l = 0, r = 0, ans = n + 1;
        long sum = 0;
        while (l < n) {
            while (r < n && sum < target) {
                sum += nums[r++];
            }
            if (sum >= target) {
                ans = Math.min (ans, r - l);
            }
            sum -= nums[l++];
        }
        return ans == n + 1 ? 0 : ans;
    }


    private int solve1(int target, int[] nums) {
        int n = nums.length;
        int[] sum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + nums[i];
        }
        int ans = n;
        for (int i = 1; i <= n; i++) {
            int l = i, r = n, mid, res = r;
            while (l <= r) {
                mid = (l + r) >>> 1;
                if (sum[mid] - sum[i - 1] >= target) {
                    res = mid;
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
            ans = Math.min (res - i + 1, ans);
        }
        return ans;
    }
}
