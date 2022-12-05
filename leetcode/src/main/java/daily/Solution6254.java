package daily;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @description
 * @date:2022/12/5 12:09
 * @author: qyl
 */
public class Solution6254 {
    public long dividePlayers(int[] skill) {
        int n = skill.length, sum = 0;
        long res = 0;
        Arrays.sort (skill);
        int avg = (int) (Arrays.stream (skill).average ( ).getAsDouble ( ) * 2);
        for (int i = 0; i < n / 2; i++) {
            if (skill[i] + skill[n - 1 - i] != avg) {
                return -1;
            }
            res += (long) skill[i] * skill[n - i - 1];
        }
        return res;
    }

    @Test
    public void test() {
        int[] skill = {3, 2, 5, 1, 3, 4};
        dividePlayers (skill);
    }
}
