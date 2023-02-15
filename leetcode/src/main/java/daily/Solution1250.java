package daily;

import java.util.Arrays;

/**
 * @description
 * @date 2023/2/15 16:19
 * @author: qyl
 */
public class Solution1250 {
    public boolean isGoodArray(int[] nums) {
        return Arrays.stream (nums).reduce (0, this::gcd) == 1;
    }

    int gcd(int x, int y) {
        return y == 0 ? x : gcd (y, x % y);
    }
}
