package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/12/10 17:04
 * @author: qyl
 */
public class Solution2302 {
    public long countSubarrays(int[] nums, long k) {
        int n = nums.length;
        long[] pre = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            pre[i] = pre[i - 1] + nums[i - 1];
        }

        long res = 0;


        int left = 1, right = left;
        while (left <= n) {
            while (right <= n && (pre[right] - pre[left - 1]) * (right - left + 1) < k) {
                right++;
            }
            res += right - left;
            left++;
        }
        return res;
    }

    @Test
    public void test() {
        System.out.println (countSubarrays (new int[]{1, 1, 1}, 5));
    }
}
