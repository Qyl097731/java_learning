package daily;

import java.util.Arrays;

/**
 * @description
 * @date:2023/1/1 19:41
 * @author: qyl
 */
public class Offer57 {
    public int[] twoSum(int[] nums, int target) {
//        return solve1(nums,target);
        return solve2 (nums, target);
    }

    private int[] solve2(int[] nums, int target) {
        int[] res = new int[]{-1, -1};
        int n = nums.length;
        int l = 0, r = n - 1;
        while (l <= r) {
            int sum = nums[l] + nums[r];
            if (sum < target) {
                l++;
            } else if (sum == target) {
                res = new int[]{nums[l], nums[r]};
            } else {
                r--;
            }
        }
        return res;
    }

    private int[] solve1(int[] nums, int target) {
        int[] res = new int[]{-1, -1};
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int id = Arrays.binarySearch (nums, i + 1, n, target - nums[i]);
            if (id >= 0) {
                res = new int[]{nums[i], nums[id]};
            }
        }
        return res;
    }
}
