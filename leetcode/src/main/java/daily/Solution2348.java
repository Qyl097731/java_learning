package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2023/1/5 19:06
 * @author: qyl
 */
public class Solution2348 {
    public long zeroFilledSubarray(int[] nums) {
        int l = 0, r = 0, n = nums.length;
        long res = 0;
        while (r < n) {
            if (r < n && nums[r] != 0) {
                r++;
                l = r;
                continue;
            }
            while (r < n && nums[r] == 0) {
                r++;
            }
            while (l < r) {
                res += r - l;
                l++;
            }
        }
        return res;
    }

    @Test
    public void test() {
        int[] nums = new int[]{0, 0, 0, 2, 0, 0};
        zeroFilledSubarray (nums);
    }
}
