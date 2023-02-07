package daily;

/**
 * @description
 * @date 2023/2/7 23:11
 * @author: qyl
 */
public class Solution80 {
    int n, len = 0;
    int[] nums;

    public int removeDuplicates(int[] nums) {
        this.nums = nums;
        this.n = nums.length;
        int l = 0;
        while (l < n) {
            int r = findRightBound (nums[l], l);
            replaceNumbers (nums, l, r);
            l = r + 1;
        }
        return len;
    }

    private void replaceNumbers(int[] nums, int l, int r) {
        int k = Math.min (2, r - l);
        for (int i = 0; i < k; i++) {
            nums[len++] = nums[l];
        }
    }

    private int findRightBound(int num, int l) {
        int r;
        for (r = l + 1; r < n; r++) {
            if (nums[r] != num) {
                break;
            }
        }
        return r;
    }
}
