package daily;

import java.util.List;

/**
 * @description
 * @date 2023/2/15 16:30
 * @author: qyl
 */
public class Solution120 {
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size ();
        int[] sum = new int[n];
        sum[0] = triangle.get (0).get (0);
        for (int i = 1; i < n; i++) {
            for (int j = i; j >= 0; j--) {
                int value = triangle.get (i).get (j);
                if (i == j) {
                    sum[j] = sum[j - 1] + value;
                } else {
                    if (j == 0) {
                        sum[j] = sum[j] + value;
                    } else {
                        sum[j] = Math.min (sum[j], sum[j - 1]) + value;
                    }
                }
            }
        }
        int res = sum[0];
        for (int i = 1; i < n; i++) {
            res = Math.min (res, sum[i]);
        }
        return res;
    }
}
