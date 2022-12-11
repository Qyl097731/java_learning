package daily;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @date:2022/12/11 22:20
 * @author: qyl
 */
public class Solution6258 {
    Map<Integer, Integer> map = new HashMap<> ( );

    public int longestSquareStreak(int[] nums) {
        Arrays.sort (nums);

        int res = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int sqrt = (int) Math.sqrt (nums[i]);
            int temp;
            if (sqrt * sqrt == nums[i]) {
                temp = map.getOrDefault (sqrt, 0) + 1;
            } else {
                temp = 1;
            }
            map.put (nums[i], temp);
            res = Math.max (temp, res);
        }
        if (res < 2) {
            return -1;
        }
        return res;
    }

    @Test
    public void test() {
        System.out.println (longestSquareStreak (new int[]{4, 5, 4}));
    }
}
