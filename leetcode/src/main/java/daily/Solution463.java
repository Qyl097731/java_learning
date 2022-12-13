package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/12/13 12:25
 * @author: qyl
 */
public class Solution463 {
    int[][] dir = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    int res = 0;

    public int islandPerimeter(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    grid[i][j] = 2;
                    dfs (grid, i, j, n, m);
                }
            }
        }
        return res;
    }

    private void dfs(int[][] grid, int x, int y, int n, int m) {
        for (int i = 0; i < 4; i++) {
            int xx = x + dir[i][0], yy = y + dir[i][1];
            if (xx >= 0 && xx < n && yy >= 0 && yy < m) {
                if (grid[xx][yy] == 1) {
                    grid[xx][yy] = 2;
                    dfs (grid, xx, yy, n, m);
                } else if (grid[xx][yy] == 0) {
                    res++;
                }
            } else {
                res++;
            }
        }
    }

    @Test
    public void test() {
        int[][] ints = {{0, 1, 0, 0}, {1, 1, 1, 0}, {0, 1, 0, 0}, {1, 1, 0, 0}};
        islandPerimeter (ints);
    }
}
