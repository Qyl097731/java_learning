package daily;

import java.util.Arrays;

/**
 * @description
 * @date:2023/1/19 21:49
 * @author: qyl
 */
public class Solution621 {
    public int leastInterval(char[] tasks, int k) {
        int res = 0, n = tasks.length;
        int[] count = new int[26];
        for (char task : tasks) {
            count[task - 'A']++;
        }

        Arrays.sort (count);

        // AXX AXX A   k = 2
        res = (count[25] - 1) * (k + 1) + 1;

        for (int i = 24; i >= 0; i--) {
            if (count[i] == count[25]) res++;
        }

        return Math.max (res, n);
    }
}
