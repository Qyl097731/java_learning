package daily;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @description
 * @date:2023/1/21 22:05
 * @author: qyl
 */
public class Solution6302 {
    public long maxScore(int[] nums1, int[] nums2, int k) {
        int n = nums1.length;
        List<int[]> list = new ArrayList<> ();
        for (int i = 0; i < n; i++) {
            list.add (new int[]{nums1[i], nums2[i]});
        }
        Collections.sort (list, (a, b) -> {
            if (a[1] == b[1]) {
                return b[0] - a[0];
            }
            return b[1] - a[1];
        });
        long minx = Arrays.stream (nums2).max ().getAsInt () + 1, sum = 0;
        boolean[] used = new boolean[n];
        for (int j = 0; j < k; j++) {
            for (int i = 0; i < n; i++) {
                if (!used[i] && minx * sum <= Math.min (minx, nums2[i]) * (sum + nums1[i])) {
                    minx = Math.min (minx, nums2[i]);
                    sum += nums1[i];
                    used[i] = true;
                }
            }
        }
    }
}
