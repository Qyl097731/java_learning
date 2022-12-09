package daily;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @description
 * @date:2022/12/9 22:35
 * @author: qyl
 */
public class Solution2294 {
    public int partitionArray(int[] nums, int k) {
        Arrays.sort (nums);
        int res = 0;
        for (int left = 0; left < nums.length; left++) {
            res++;
            int min = nums[left], max = nums[left];
            int right = left + 1;
            while (right < nums.length) {
                min = Math.min (min, nums[right]);
                max = Math.max (max, nums[right]);
                if (max - min > k) {
                    left = right - 1;
                    break;
                }
                right++;
            }
            if (right == nums.length) {
                return res;
            }
        }
        return res;
    }

    @Test
    public void test() {
        int[] nums = {1, 2, 3, 5, 6};
        System.out.println (partitionArray (nums, 2));
    }
}
