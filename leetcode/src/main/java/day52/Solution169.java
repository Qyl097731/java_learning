package day52;

import java.util.Arrays;

/**
 * @description
 * @date:2022/11/24 10:30
 * @author: qyl
 */
public class Solution169 {
    public int majorityElement(int[] nums) {
//        return solveA(nums);
        return solveB (nums);
    }

    private int solveB(int[] nums) {
        int n = nums.length, count = 0, candidate = -1;
        for (int i = 0; i < n; i++) {
            if (count == 0) {
                candidate = nums[i];
            }
            count += candidate == nums[i] ? 1 : -1;
        }
        return candidate;
    }

    private int solveA(int[] nums) {
        Arrays.sort (nums);
        return nums[nums.length / 2];
    }


}
