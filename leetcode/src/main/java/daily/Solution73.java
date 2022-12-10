package daily;

/**
 * @description
 * @date:2022/12/10 21:23
 * @author: qyl
 */
public class Solution73 {
    public void setZeroes(int[][] matrix) {
        long rowS = 0, colS = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    rowS |= (1L << i);
                    colS |= (1L << j);
                }
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if ((rowS & (1L << i)) != 0 || (colS & (1L << j)) != 0) {
                    matrix[i][j] = 0;
                }
            }
        }
    }
}
