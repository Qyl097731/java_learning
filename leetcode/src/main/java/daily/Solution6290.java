package daily;

import org.junit.jupiter.api.Test;


/**
 * @description
 * @date:2023/1/7 23:04
 * @author: qyl
 */
public class Solution6290 {
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
            if (check (mid, sum, r, k, n)) {
                res = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return res;
    }

    private boolean check(long mid, long[] sum, int r, int k, int n) {
        long[] temp = new long[n + 1];
        long need = 0;
        for (int i = 0; i < n; i++) {
            if (i >= 1) {
                temp[i] += temp[i - 1];
            }
            if (sum[i] + temp[i] < mid) {
                long d = mid - sum[i] - temp[i];
                need += d;
                temp[i] += d;
                temp[Math.min (n, i + 2 * r + 1)] -= d;
            }
        }
        if (need <= k) {
            return true;
        }
        return false;
    }

    @Test
    public void test() {
        maxPower (new int[]{1, 2, 4, 5, 0}, 1, 2);
    }
}
