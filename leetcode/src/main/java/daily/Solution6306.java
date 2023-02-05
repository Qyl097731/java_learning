package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date 2023/2/5 0:27
 * @author: qyl
 */
public class Solution6306 {
    int n, m;
    int[][] g;

    public boolean isPossibleToCutPath(int[][] grid) {
        g = grid;
        n = grid.length;
        m = grid[0].length;
        return !dfs (0, 0) || !dfs (0, 0);
    }

    private boolean dfs(int x, int y) {
        if (x == n - 1 && y == m - 1) {
            return true;
        }
        g[x][y] = 0;
        return x + 1 < n && g[x + 1][y] == 1 && dfs (x + 1, y)
                || y + 1 < m && g[x][y + 1] == 1 && dfs (x, y + 1);
    }

    @Test
    public void test() {
        isPossibleToCutPath (new int[][]{{1, 1, 1, 0, 0}, {1, 0, 1, 0, 0}, {1, 1, 1, 1, 1}, {0, 0, 1, 1, 1}, {0, 0, 1, 1, 1}});
    }
}
