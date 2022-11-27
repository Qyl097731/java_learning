package day53;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @description
 * @date:2022/11/26 22:19
 * @author: qyl
 */
public class Solution6250 {
    public int[][] onesMinusZeros(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        int[][] row = new int[n][2];
        int[][] col = new int[m][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                row[i][0] += grid[i][j] == 0 ? 1 : 0;
                row[i][1] += grid[i][j] == 1 ? 1 : 0;
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                col[i][0] += grid[j][i] == 0 ? 1 : 0;
                col[i][1] += grid[j][i] == 1 ? 1 : 0;
            }
        }
        int[][] res = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                res[i][j] = row[i][1] + col[j][1] - row[i][0] - col[j][0];
            }
        }
        return res;
    }

    @Test
    public void test() {
        int[][] grid = {{0, 1, 1}, {1, 0, 1}, {0, 0, 1}};
        System.out.println (Arrays.deepToString (onesMinusZeros (grid)));
        int[][] g2 = {{1, 1, 1}, {1, 1, 1}};
        System.out.println (Arrays.deepToString (onesMinusZeros (g2)));
    }
}
