package daily;

import java.util.Arrays;

/**
 * @description
 * @date:2023/1/20 19:13
 * @author: qyl
 */
public class Solution581 {
    public int findUnsortedSubarray(int[] nums) {
//        return solve1(nums);
        return solve2 (nums);
    }

    private int solve2(int[] nums) {
        int n = nums.length;
        int maxn = Integer.MIN_VALUE, right = -1;
        int minn = Integer.MAX_VALUE, left = -1;
        for (int i = 0; i < n; i++) {
            if (maxn <= nums[i]) {
                maxn = nums[i];
            } else {
                right = i;
            }
            if (minn >= nums[n - i - 1]) {
                minn = nums[n - i - 1];
            } else {
                left = n - i - 1;
            }
        }
        return right == -1 ? 0 : right - left + 1;
    }

    private int solve1(int[] nums) {
        int n = nums.length;
        int[] numsCopy = Arrays.copyOf (nums, n);
        Arrays.sort (nums);

        if (Arrays.equals (numsCopy, nums)) {
            return 0;
        }

        int l = 0, r = n - 1;

        while (l < n) {
            if (numsCopy[l] != nums[l]) {
                break;
            }
            l++;
        }
        while (r >= l) {
            if (numsCopy[r] != nums[r]) {
                break;
            }
            r--;
        }

        return r - l;
    }
}
