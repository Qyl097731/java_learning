package daily;

/**
 * @description
 * @date 2023/2/7 23:49
 * @author: qyl
 */
public class Solution81 {
    public boolean search(int[] nums, int target) {
//        return solve1(nums,target);
        return solve2 (nums, target);

    }

    private boolean solve2(int[] nums, int target) {
        int l = 0, r = nums.length - 1, mid;
        while (l <= r) {
            mid = (l + r) / 2;
            if (target == nums[mid]) {
                return true;
            }
            if (nums[l] == nums[mid] && nums[mid] == nums[r]) {
                l++;
                r--;
            } else if (nums[l] <= nums[mid]) {
                if (nums[l] <= target && target <= nums[mid]) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            } else {
                if (nums[mid] < target && target <= nums[r]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }
        return false;
    }

    private boolean solve1(int[] nums, int target) {
        return search (nums, target, 0, nums.length - 1);
    }

    private boolean search(int[] nums, int target, int l, int r) {
        if (l > r) {
            return false;
        }
        if (l == r) {
            return target == nums[l];
        }
        int mid = (l + r) / 2;
        return search (nums, target, l, mid) || search (nums, target, mid + 1, r);
    }
}
