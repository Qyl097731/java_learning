package daily;

import org.junit.jupiter.api.Test;


/**
 * @description
 * @date:2023/1/7 23:04
 * @author: qyl
 */
public class D {
    public long maxPower(int[] stations, int r, int k) {
        int n = stations.length;
        long[] sum = new long[n + 2];
        for (int i = 0; i < n; i++) {
            sum[Math.max (0, i - r)] += stations[i];
            sum[Math.min (n, i + r + 1)] -= stations[i];
        }
        for (int i = 1; i <= n; i++) {
            sum[i] += sum[i - 1];
        }
        for (int i = 0; i <= n; i++) {

        }
        long res = 0;
        long left = 0, right = (long) (2e10) + 5, mid;
        while (left <= right) {
            mid = (left + right) / 2;
            if (check (mid)) {
                res = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return res;
    }

    @Test
    public void test() {
        maxPower (new int[]{1, 2, 4, 5, 0}, 1, 2);
    }
}
