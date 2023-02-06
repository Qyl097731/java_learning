package daily;

/**
 * @description
 * @date 2023/2/6 23:19
 * @author: qyl
 */
public class Solution74 {
    public boolean searchMatrix(int[][] matrix, int target) {
        int n = matrix.length, m = matrix[0].length;
        int[] nums = new int[n * m];
        for (int i = 0, idx = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                nums[idx++] = matrix[i][j];
            }
        }
        int l = 0, r = m * n, mid;
        while (l <= r) {
            mid = (l + r) / 2;
            if (nums[mid] == target) {
                return true;
            } else if (nums[mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return false;
    }
}
