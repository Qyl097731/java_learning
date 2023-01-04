package daily;

import java.util.Arrays;

/**
 * @description
 * @date:2023/1/4 17:42
 * @author: qyl
 */
public class Offer61 {
    public boolean isStraight(int[] nums) {
        Arrays.sort (nums);
        int[] cnt = new int[14];
        int n = nums.length;
        int space = 0;
        for (int i = 0; i < n; i++) {
            cnt[nums[i]]++;
            if (nums[i] != 0 && cnt[nums[i]] > 1) {
                return false;
            }
            if (i >= 1 && nums[i - 1] == 0) {
                continue;
            }
            if (i >= 1) {
                space += nums[i] - nums[i - 1] - 1;
            }
        }
        return space == cnt[0];
    }
}
