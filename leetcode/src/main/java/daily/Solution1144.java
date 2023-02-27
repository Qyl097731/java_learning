package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date 2023/2/27 22:46
 * @author: qyl
 */
public class Solution1144 {
    public int movesToMakeZigzag(int[] nums) {
        return Math.min (countOperations (nums, 0), countOperations (nums, 1));
    }

    private int countOperations(int[] nums, int start) {
        int n = nums.length, res = 0;
        while (start < n) {
            int cnt = 0;
            if (start - 1 >= 0) {
                cnt = Math.max (0, nums[start] - nums[start - 1] + 1);
            }
            if (start + 1 < n) {
                cnt += Math.max (cnt, nums[start] - nums[start + 1] + 1);
            }
            res += cnt;
            start += 2;
        }
        return res;
    }

    @Test
    public void test() {
        movesToMakeZigzag (new int[]{1, 2, 3});
    }
}
