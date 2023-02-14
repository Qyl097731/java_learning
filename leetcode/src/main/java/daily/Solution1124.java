package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date 2023/2/14 17:40
 * @author: qyl
 */
public class Solution1124 {
    public int longestWPI(int[] hours) {
        int n = hours.length;
        int res = 0;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (hours[j] > 8) {
                    sum++;
                } else {
                    sum--;
                }
                if (sum > 0) {
                    res = Math.max (res, j - i + 1);
                }
            }
        }
        return res;
    }

    private boolean check(int[] hours, int len, int n) {
        int sum = 0, l = 0, r;
        for (r = 0; r < len; r++) {
            if (hours[r] > 8) {
                sum++;
            } else {
                sum--;
            }
        }
        boolean flag = sum > 0;
        while (r < n) {
            if (hours[r] > 8) {
                sum++;
            } else {
                sum--;
            }
            if (hours[l] > 8) {
                sum--;
            } else {
                sum++;
            }
            l++;
            r++;
            flag |= sum > 0;
        }
        return flag;
    }

    @Test
    public void test() {
        longestWPI (new int[]{9, 6, 9});
    }
}
