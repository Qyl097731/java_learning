package daily;

/**
 * @description
 * @date 2023/2/16 22:53
 * @author: qyl
 */
public class Solution88 {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int r = m + n - 1, idx1 = m - 1, idx2 = n - 1;
        while (idx2 >= 0 && idx1 >= 0) {
            if (nums1[idx1] >= nums2[idx2]) {
                nums1[r--] = nums1[idx1--];
            } else {
                nums1[r--] = nums2[idx2--];
            }
        }
        while (idx2 >= 0) {
            nums1[r--] = nums2[idx2--];
        }
    }
}
