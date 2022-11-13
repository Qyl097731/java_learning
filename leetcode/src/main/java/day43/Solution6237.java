package day43;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @description
 * @date:2022/11/12 22:28
 * @author: qyl
 */
public class Solution6237 {
    public int distinctAverages(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        Set<Double> set = new HashSet<>();
        for (int i = 0; i * 2 < n; i++) {
            set.add((nums[i] + nums[n - i - 1]) / 2.0);
        }
        return set.size();
    }

    @Test
    public void test() {
        distinctAverages(new int[]{4, 1, 4, 0, 3, 5});
    }
}
