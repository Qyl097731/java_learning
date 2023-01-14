package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2023/1/14 20:35
 * @author: qyl
 */
public class Solution240 {
    public boolean searchMatrix(int[][] matrix, int target) {
        int n = matrix.length, m = matrix[0].length;
        for (int i = 0; i < n; i++) {
            if (check (matrix, target, i, m)) {
                return true;
            }
        }
        return false;
    }

    private boolean check(int[][] matrix, int target, int i, int m) {
        int l = 0, r = m - 1, mid;
        while (l <= r) {
            mid = (l + r) / 2;
            if (matrix[i][mid] == target) {
                return true;
            } else if (matrix[i][mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return false;
    }

    @Test
    public void test() {
        int[][] nums = {{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}, {11, 12, 13, 14, 15}, {16, 17, 18, 19, 20}, {21, 22, 23, 24, 25}};
        searchMatrix (nums, 19);
    }
}
