package daily;

import java.util.Arrays;

/**
 * @description
 * @date:2022/12/7 23:41
 * @author: qyl
 */
public class Solution2285 {
    public long maximumImportance(int n, int[][] roads) {
        int[] du = new int[n];
        for (int[] road : roads) {
            du[road[0]] += 2;
            du[road[1]] += 2;
        }
        Arrays.sort (du);
        long res = 0;
        for (int i = 1; i <= n; i++) {
            res += (long) du[i - 1] * i / 2;
        }
        return res;
    }
}
