package daily;

import java.util.Arrays;

/**
 * @description
 * @date:2023/1/15 23:57
 * @author: qyl
 */
public class Solution287 {
    public int findDuplicate(int[] nums) {
        int res = 0;
        Arrays.sort (nums);
        int pre = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (pre == nums[i]) {
                res = pre;
                break;
            }
            pre = nums[i];
        }
        return res;
    }
}
