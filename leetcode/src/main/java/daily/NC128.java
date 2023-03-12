package daily;

import java.util.Arrays;

/**
 * @description
 * @date 2023/3/11 14:52
 * @author: qyl
 */
public class NC128 {
    public long maxWater(int[] arr) {
        int n = arr.length;
        int[] maxLeft = new int[n], maxRight = new int[n];
        for (int i = 0; i < n; i++) {
            maxLeft[i] = i > 1 ? Math.max (maxLeft[i - 1], arr[i]) : arr[i];
        }
        System.out.println (Arrays.toString (maxLeft));
        for (int i = n - 1; i >= 0; i--) {
            maxRight[i] = i + 1 < n ? Math.max (maxRight[i + 1], arr[i]) : arr[i];
        }
        System.out.println (Arrays.toString (maxRight));
        long result = 0;
        for (int i = 0; i < n; i++) {
            result += Math.min (maxLeft[i], maxRight[i]) - arr[i];
        }
        return result;
    }
}
