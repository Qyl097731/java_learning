package day25;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @date:2022/10/17 19:56
 * @author: qyl
 */
class Solution904 {
    @Test
    public void test() {
        Assertions.assertEquals(totalFruit(new int[]{1, 0, 1, 4, 1, 4, 1, 2, 3}), 5);
    }


    public int totalFruit(int[] fruits) {
        int res = 0, n = fruits.length;
        Map<Integer, Integer> cnt = new HashMap<>();
        int left = 0, right = 0;
        for (; right < n; right++) {
            cnt.put(fruits[right], cnt.getOrDefault(fruits[right], 0) + 1);
            while (cnt.size() > 2) {
                cnt.put(fruits[left], cnt.get(fruits[left]) - 1);
                if (cnt.get(fruits[left]) == 0) {
                    cnt.remove(fruits[left]);
                }
                left++;
            }
            res = Math.max(res, right - left + 1);
        }
        return res;
    }
}
