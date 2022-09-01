package day07;

/**
 * @author qyl
 * @program Pro48.java
 * @Description 旋转图像
 * @createTime 2022-09-01 13:01
 */
public class Pro48 {
    public static void main(String[] args) {
        new Solution48().rotate(new int[][]{{1,2,3},{4,5,6},{7,8,9}});
    }
}

class Solution48 {
    public void rotate(int[][] matrix) {
        int n = matrix.length, m = matrix[0].length;
        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < m; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n - 1 - i][j];
                matrix[n - 1 - i][j] = temp;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }

}