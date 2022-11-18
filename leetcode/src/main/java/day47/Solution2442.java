package day47;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @description
 * @date:2022/11/18 1:50
 * @author: qyl
 */
public class Solution2442 {
    public int countDistinctIntegers(int[] nums) {
        int n = nums.length;
        StringBuilder[] s = new StringBuilder[n];
        for (int i = 0; i < n; i++) {
            s[i] = new StringBuilder(nums[i] + "");
        }
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            set.add(nums[i]);
            set.add(Integer.parseInt(s[i].reverse().toString()));
        }
        return set.size();
    }

    @Test
    public void test() {
        System.out.println(countDistinctIntegers(new int[]{2, 2, 2}));
    }
}
