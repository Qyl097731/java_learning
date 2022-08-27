package day04;

/**
 * @description 最大矩形
 * @date:2022/8/27 23:26
 * @author: qyl
 */
public class Pro85 {
    public static void main(String[] args) {
        System.out.println(new Solution75().maximalRectangle(new char[][]{{'1', '0', '1', '0', '0'}, {'1', '0', '1', '1', '1'}, {'1', '1', '1', '1', '1'}, {
                '1', '0', '0', '1', '0'}}));
    }
}

class Solution75 {
    int[][] a = new int[2000][2000];
    int n, m;
    int res = 0, temp = 0, width = 0;

    public int maximalRectangle(char[][] matrix) {
        if (matrix == null) {
            return 0;
        }
        n = matrix.length;
        m = matrix[0].length;

        int[][] left = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == '0') {
                    left[i][j] = 0;
                } else {
                    left[i][j] = j >= 1 ? left[i][j - 1] + 1 : 1;
                }
                res = Math.max(res, left[i][j]);
            }
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == '1') {
                    width = temp = left[i][j];
                    for (int k = i - 1; k >= 0; k--) {
                        width = Math.min(width, left[k][j]);
                        temp = Math.max(temp, (i - k + 1) * width);
                    }
                    res = Math.max(temp, res);
                }
            }
        }
        return res;
    }
}
