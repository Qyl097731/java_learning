package daily;

/**
 * @description
 * @date:2022/12/10 21:23
 * @author: qyl
 */
public class Solution73 {
    public void setZeroes(int[][] matrix) {
        int n = matrix.length, m = matrix[0].length;
        boolean[] zeroX = new boolean[n], zeroY = new boolean[m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == 0) {
                    zeroX[i] = true;
                    zeroY[j] = true;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (zeroX[i] || zeroY[j]) {
                    matrix[i][j] = 0;
                }
            }
        }
    }
}
