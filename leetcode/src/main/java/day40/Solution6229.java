package day40;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @description
 * @date:2022/11/6 9:24
 * @author: qyl
 */
public class Solution6229 {
    public long maximumSubarraySum(int[] nums, int k) {
        int n = nums.length;
        HashSet<Integer> set = new HashSet<>();
        long[] pre = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            pre[i] = pre[i - 1] + nums[i - 1];
            if (i < k) {
                set.add(nums[i-1]);
            }
        }


        long res = 0;
        for (int i = 0; i + k < n; i++) {
            set.add(nums[i + k]);
            if (set.size() == k) {
                res = Math.max(res, pre[i + k] - pre[i]);
            }
            set.remove(nums[i]);
        }
        return res;
    }

    @Test
    public void test() {
        maximumSubarraySum(new int[]{1,5,4,2,9,9,9,
        },3);
    }
}
