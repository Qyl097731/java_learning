package daily;

import java.util.Arrays;

/**
 * @description
 * @date:2023/1/22 17:00
 * @author: qyl
 */
public class Solution2535 {
    public int differenceOfSum(int[] nums) {
        return Arrays.stream (nums).sum () - getSum (nums);
    }

    private int getSum(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            while (num > 0) {
                sum += num % 10;
                num /= 10;
            }
        }
        return sum;
    }
}
