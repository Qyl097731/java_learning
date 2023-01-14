package daily;

import java.util.Arrays;

/**
 * @description
 * @date:2023/1/14 20:11
 * @author: qyl
 */
public class Solution1819 {
    public int countDifferentSubsequenceGCDs(int[] nums) {
        int maxV = Arrays.stream (nums).max ().getAsInt ();
        boolean[] exists = new boolean[maxV + 1];
        for (int num : nums) {
            exists[num] = true;
        }
        int res = 0;
        for (int i = 1; i <= maxV; i++) {
            int last = 0;
            for (int j = i; j <= maxV; j += i) {
                if (exists[j]) {
                    if (last == 0) {
                        last = j;
                    } else {
                        last = gcd (last, j);
                    }
                    if (last == i) {
                        res++;
                        break;
                    }
                }
            }
        }
        return res;
    }

    private int gcd(int x, int y) {
        return y == 0 ? x : gcd (y, x % y);
    }
}
