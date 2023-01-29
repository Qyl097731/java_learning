package daily;

import java.util.Arrays;

/**
 * @description
 * @date:2023/1/29 22:17
 * @author: qyl
 */
public class Solution6339 {
    public long putMarbles(int[] weights, int k) {
        int n = weights.length;
        for (int i = 1; i < n; i++) {
            weights[i] += weights[i + 1];
        }
        Arrays.sort (weights, 1, n - 1);
        System.out.println (Arrays.toString (weights));
        long res = 0;
        for (int i = 0; i < k; i++) {
            res += weights[n - 2 - k] - weights[k + 1];
        }
        return res;
    }
}
