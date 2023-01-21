package daily;

import java.util.Arrays;

/**
 * @description
 * @date:2023/1/21 22:05
 * @author: qyl
 */
public class B {
    public long minOperations(int[] nums1, int[] nums2, int k) {
        long res1 = 0, res2 = 0;
        int n = nums1.length;
        if (k == 0) {
            if (Arrays.equals (nums1, nums2)) {
                return 0;
            } else {
                return -1;
            }
        }
        for (int i = 0; i < n; i++) {
            if (nums1[i] > nums2[i]) {
                if ((nums1[i] - nums2[i]) % k == 0) {
                    res1 += (nums1[i] - nums2[i]) / k;
                } else {
                    return -1;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (nums1[i] < nums2[i]) {
                if ((nums2[i] - nums1[i]) % k == 0) {
                    res2 += (nums2[i] - nums1[i]) / k;
                } else {
                    return -1;
                }
            }
        }
        if (res1 == res2) {
            return res1;
        } else {
            return -1;
        }
    }
}
