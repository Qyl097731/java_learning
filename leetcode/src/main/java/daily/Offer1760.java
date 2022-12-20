package daily;

import java.util.Arrays;

/**
 * @description
 * @date:2022/12/20 16:58
 * @author: qyl
 */
public class Offer1760 {
    public int minimumSize(int[] nums, int maxOperations) {
        int n = nums.length;
        int l = 1, r = Arrays.stream (nums).max ( ).getAsInt ( ), mid;
        int res = 0;
        while (l <= r) {
            mid = l + (r - l) / 2;
            long temp = 0;
            for (int num : nums) {
                if (num > mid) {
                    temp += (num - 1) / mid;
                }
            }
            if (temp <= maxOperations) {
                res = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return res;
    }
}
