package daily;

import java.util.Arrays;

/**
 * @description
 * @date:2022/12/6 14:08
 * @author: qyl
 */
public class Solution2279 {
    public int maximumBags(int[] capacity, int[] rocks, int additionalRocks) {
        int n = capacity.length;
        int sum = 0;
        int[] left = new int[n];
        for (int i = 0; i < n; i++) {
            left[i] = capacity[i] - rocks[i];
        }
        Arrays.sort (left);
        for (int i = 0; i < n; i++) {
            if (additionalRocks < left[i]) {
                break;
            }
            additionalRocks -= left[i];
            sum++;
        }
        return sum;
    }
}
