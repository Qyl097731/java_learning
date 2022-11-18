package day47;

import java.util.Arrays;

/**
 * @description
 * @date:2022/11/18 1:50
 * @author: qyl
 */
public class Solution2441 {
    int[] used = new int[100000];

    public int findMaxK(int[] nums) {
        int n = nums.length;
        int res = -1;
        Arrays.sort(nums);
        for (int i = 0; i < n; i++) {
            if (nums[i] < 0) {
                used[Math.abs(nums[i])]++;
            } else {
                if (used[nums[i]] > 0) {
                    res = Math.max(res, nums[i]);
                }
            }
        }
        return res;
    }
}
