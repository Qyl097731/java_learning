package day26;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @date:2022/10/18 12:02
 * @author: qyl
 */
class Solution59 {
    int[][] dir = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    List<Integer> res = new ArrayList<>();
    int tot = 1;
    int[][] vis;
    int[][] matrix;

    public int[][] generateMatrix(int n) {
        matrix = new int[n][n];
        vis = new int[n][n];
        vis[0][0] = 1;
        matrix[0][0] = tot;
        dfs(n, n, 0, 0, matrix, 0);
        return matrix;
    }

    private void dfs(int n, int m, int x, int y, int[][] matrix, int cur) {
        tot++;
        if (tot == n * m + 1) {
            return;
        }

        for (int i = 0; i < 4; i++) {
            int xx = x + dir[(cur + i) % 4][0], yy = y + dir[(cur + i) % 4][1];
            if (tot == n * m + 1) return;
            if (xx >= 0 && xx < n && yy >= 0 && yy < m && vis[xx][yy] == 0) {
                matrix[xx][yy] = tot;
                vis[xx][yy] = 1;
                dfs(n, m, xx, yy, matrix, (cur + i) % 4);
            }
        }
    }
}
