package day40;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/11/6 9:24
 * @author: qyl
 */
public class Solution6300 {
    public long maximumSubarraySum(int[] nums, int k) {
        int n = nums.length;
        int[] count = new int[100007];
        long[] pre = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            pre[i] = pre[i - 1] + nums[i - 1];
        }
        long res = 0;
        int l = 0, r = 0;
        while (r < n && r - l + 1 <= k) {
            count[nums[r]]++;
            if (count[nums[r]] > 1) {
                while (count[nums[r]] > 1) {
                    count[nums[l]]--;
                    l++;
                }
            }
            if (r - l + 1 == k) {
                res = Math.max(res, pre[r + 1] - pre[l]);
                count[nums[l]]--;
                l++;
            }
            r++;
        }
        return res;
    }

    @Test
    public static void main(String[] args) {
        System.out.println(new Solution6300().maximumSubarraySum(new int[]{9, 9, 9, 1, 2, 3
        }, 3));
    }
}
