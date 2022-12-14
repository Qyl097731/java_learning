package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/12/14 12:14
 * @author: qyl
 */
public class Solution2319 {
    public boolean checkXMatrix(int[][] grid) {
        int n = grid.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i == j || (i + j == n - 1))) {
                    if (grid[i][j] == 0) {
                        return false;
                    }
                } else {
                    if (grid[i][j] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Test
    public void test() {
        int[][] a = {{2, 0, 0, 1}, {0, 3, 1, 0}, {0, 5, 2, 0}, {4, 0, 0, 2}};
        checkXMatrix (a);
    }
}
