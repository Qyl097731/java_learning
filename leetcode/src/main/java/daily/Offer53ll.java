package daily;

import java.util.HashSet;

/**
 * @description
 * @date:2022/12/19 17:02
 * @author: qyl
 */
public class Offer53ll {
    public int missingNumber(int[] nums) {
//        return solve1(nums);
//        return solve2 (nums);
//        return solve3(nums);
        return solve4 (nums);
    }

    private int solve4(int[] nums) {
        int res = 0, n = nums.length;
        for (int num : nums) {
            res ^= num;
        }
        for (int i = 0; i < n; i++) {
            res ^= i;
        }
        return res;
    }

    private int solve3(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] != i) {
                return i;
            }
        }
        return n;
    }

    private int solve2(int[] nums) {
        HashSet<Integer> set = new HashSet<> ( );
        int n = nums.length;
        for (int num : nums) {
            set.add (num);
        }
        for (int i = 0; i <= n; i++) {
            if (!set.contains (i)) {
                return i;
            }
        }
        return -1;
    }

    private int solve1(int[] nums) {
        int i = 0;
        while (true) {
            if (find (i, nums) == -1) {
                return i;
            }
            i++;
        }
    }

    private int find(int i, int[] nums) {
        int l = 0, r = nums.length - 1, mid;
        int res = -1;
        while (l <= r) {
            mid = l + r >> 1;
            if (nums[mid] == i) {
                res = mid;
                break;
            } else if (nums[mid] > i) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return res;
    }
}
