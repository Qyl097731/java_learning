package daily;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/12/12 10:19
 * @author: qyl
 */
public class Solution1781 {
    public int beautySum(String s) {
        int n = s.length ( );
        int res = 0;
        for (int i = 0; i < n; i++) {
            int[] cnt = new int[26];
            for (int j = i; j < n; j++) {
                cnt[s.charAt (j) - 'a']++;
                int min = Integer.MAX_VALUE, max = -1;
                for (int c : cnt) {
                    if (c != 0) {
                        min = Math.min (c, min);
                        max = Math.max (c, max);
                    }
                }
                res += max - min;
            }
        }
        return res;
    }

    @Test
    public void test() {
        Assertions.assertEquals (beautySum ("aabcb"), 5);
    }
}
