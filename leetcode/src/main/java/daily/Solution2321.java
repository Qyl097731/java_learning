package daily;

/**
 * @description
 * @date:2022/12/14 14:08
 * @author: qyl
 */
public class Solution2321 {
    public int maximumsSplicedArray(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int sum1 = 0, sum2 = 0;
        for (int i = 0; i < n; i++) {
            sum1 += nums1[i];
            sum2 += nums2[i];
        }
        return Math.max (sum1 + cal (nums2, nums1), sum2 + cal (nums1, nums2));
    }

    public int cal(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int[] dif = new int[n + 1];
        int sum = 0, f = 0;

        for (int i = 0; i < n; i++) {
            dif[i] = nums1[i] - nums2[i];
        }

        for (int i = 0; i < n; i++) {
            f = Math.max (f + dif[i], 0);
            sum = Math.max (sum, f);
        }
        return sum;
    }
}
