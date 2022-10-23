package day31;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/10/23 22:18
 * @author: qyl
 */
public class Solution6224 {
    @Test
    public void test() {
        System.out.println(subarrayGCD(new int[]{3, 3, 4, 1, 2}, 1));
    }

    public int subarrayGCD(int[] nums, int k) {
        int res = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] < k) {
                continue;
            }
            res += nums[i] == k ? 1 : 0;
            int temp = nums[i];
            for (int j = i + 1; j < n; j++) {
                temp = gcd(temp, nums[j]);
                res += temp == k ? 1 : 0;
            }
        }
        return res;
    }

    private int gcd(int num, int num1) {
        return num1 == 0 ? num : gcd(num1, num % num1);
    }


}
