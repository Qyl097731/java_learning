package daily;

import java.util.Arrays;

/**
 * @description
 * @date:2022/12/9 22:35
 * @author: qyl
 */
public class Solution2293 {
    public int minMaxGame(int[] nums) {
        while (nums.length != 1) {
            for (int i = 0; i < nums.length / 2; i++) {
                if ((i & 1) == 0) {
                    nums[i] = Math.min (nums[i * 2], nums[i * 2 + 1]);
                } else {
                    nums[i] = Math.max (nums[i * 2], nums[i * 2 + 1]);
                }
            }
            nums = Arrays.copyOfRange (nums, 0, nums.length / 2);
        }
        return nums[0];
    }
}
