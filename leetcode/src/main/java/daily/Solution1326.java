package daily;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @description
 * @date 2023/2/21 23:33
 * @author: qyl
 */
public class Solution1326 {
    public int minTaps(int n, int[] ranges) {
        int[] dp = new int[n + 1];
        int[][] intervals = new int[n][2];
        for (int i = 0; i < n; i++) {
            intervals[i] = new int[]{Math.max (i - ranges[i], 0), Math.min (i + ranges[i], n)};
        }
        Arrays.fill (dp, Integer.MAX_VALUE);
        Arrays.sort (intervals, Comparator.comparingInt (a -> a[0]));
        dp[0] = 0;
        for (int[] interval : intervals) {
            int l = interval[0], r = interval[1];
            if (dp[l] == Integer.MAX_VALUE) {
                return -1;
            }
            for (int i = l; i <= r; i++) {
                dp[i] = Math.min (dp[i], dp[l] + 1);
            }
        }
        return dp[n];
    }
}
