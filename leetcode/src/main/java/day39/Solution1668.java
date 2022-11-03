package day39;

import java.util.Arrays;

/**
 * @description
 * @date:2022/11/3 14:41
 * @author: qyl
 */
public class Solution1668 {
    public int maxRepeating(String sequence, String word) {
        int n = sequence.length(), m = word.length();
        int[] f = new int[n + 1];

        for (int i = m; i <= n; i++) {
            boolean valid = true;
            for (int j = 0; j < m; j++) {
                if (sequence.charAt(i - m + j) != word.charAt(j)) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                f[i] = f[i - m] + 1;
            }
        }
        return Arrays.stream(f).max().getAsInt();
    }
}
