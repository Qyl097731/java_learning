package daily;

import java.util.Arrays;

/**
 * @description
 * @date:2022/12/20 17:12
 * @author: qyl
 */
public class Solution2333 {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        long res = 0;
        int n = nums1.length;
        for (int i = 0; i < n; i++) {
            nums1[i] -= nums2[i];
            nums1[i] = Math.abs (nums1[i]);
            res += (long) nums1[i] * nums1[i];
        }
        int op = k1 + k2;
        Arrays.sort (nums1);
        for (int i = n - 1; i >= 0; i--) {
            int m = n - i;
            long v = nums1[i], c = m * (v - (i > 0 ? nums1[i - 1] : 0));
            res -= v * v;
            if (c < op) {
                op -= c;
                continue;
            }
            v -= op / m;
            return res + op % m * (v - 1) * (v - 1) + (m - op % m) * v * v;
        }
        return res;
    }
}
