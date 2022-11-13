package day44;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/11/13 10:27
 * @author: qyl
 */
public class Solution6234 {
    public int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public int subarrayLCM(int[] nums, int k) {
        int res = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (gcd(nums[i], k) == 1 && nums[i] != 1) {
                continue;
            }
            res += nums[i] == k ? 1 : 0;
            int gcd = nums[i], pre = nums[i], preg = nums[i];
            for (int j = i + 1; j < n; j++) {
                gcd = gcd(pre, nums[j]);
                res += (pre * nums[j]) / gcd == k ? 1 : 0;
                pre = (pre * nums[j]) / gcd;
            }
        }
        return res;
    }

    @Test
    public void test() {

        System.out.println(subarrayLCM(new int[]{3, 6, 2, 7, 1}, 6) == 4);

        System.out.println(subarrayLCM(new int[]{13, 2, 14, 9, 13, 1, 11, 1, 8}, 11) == 4);
        System.out.println(subarrayLCM(new int[]{2, 1, 1, 5}, 5) == 3);

    }
}
