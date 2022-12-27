package daily;

/**
 * @description
 * @date:2022/12/27 19:24
 * @author: qyl
 */
public class Offer04 {
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
//        return solve1(matrix,target);
        return solve2 (matrix, target);
    }

    private boolean solve2(int[][] matrix, int target) {
        for (int[] ints : matrix) {
            if (findCol (ints, target) != -1) {
                return true;
            }
        }
        return false;
    }

    private int findCol(int[] matrix, int target) {
        int l = 0, r = matrix.length - 1, mid, res = -1;
        while (l <= r) {
            mid = (l + r) / 2;
            if (matrix[mid] == target) {
                res = mid;
                break;
            } else if (matrix[mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return res;
    }

    private boolean solve1(int[][] matrix, int target) {
        int n = matrix.length;
        if (n == 0) return false;
        int m = matrix[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == target) {
                    return true;
                }
            }
        }
        return false;
    }
}
