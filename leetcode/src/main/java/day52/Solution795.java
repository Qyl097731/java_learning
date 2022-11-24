package day52;

import org.junit.jupiter.api.Test;

/**
 * @date:2022/11/24 0:13
 * @author: qyl
 */
public class Solution795 {
    public int numSubarrayBoundedMax(int[] nums, int left, int right) {
//        return solveA (nums, left, right);
        return solveB (nums, left, right);
    }

    private int solveB(int[] nums, int left, int right) {
        return count (nums, right) - count (nums, left - 1);
    }

    private int count(int[] nums, int lower) {
        int res = 0, x = 0;
        for (int num : nums) {
            x = num <= lower ? x + 1 : 0;
            res += x;
        }
        return res;
    }

    private int solveA(int[] nums, int left, int right) {
        int n = nums.length;
        int res = 0, last1 = -1, last2 = -1;
        for (int i = 0; i < n; i++) {
            if (nums[i] <= right && left <= nums[i]) {
                last1 = i;
            } else if (nums[i] > right) {
                last2 = i;
                last1 = -1;
            }
            if (last1 != -1) {
                res += last1 - last2;
            }
        }
        return res;
    }


    @Test
    public void test() {
        int[] nums = {73, 55, 36, 5, 55, 14, 9, 7, 72, 52};
        System.out.println (numSubarrayBoundedMax (nums, 32, 69));
    }
}
