package day26;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @date:2022/10/18 11:21
 * @author: qyl
 */
class Solution54 {
    int[][] dir = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    List<Integer> res = new ArrayList<>();
    int tot = 0;
    int[][] vis;

    public List<Integer> spiralOrder(int[][] matrix) {
        int n = matrix.length, m = matrix[0].length;
        vis = new int[n][m];
        vis[0][0] = 1;
        res.add(matrix[0][0]);
        dfs(n, m, 0, 0, matrix,0);
        return res;
    }

    private void dfs(int n, int m, int x, int y, int[][] matrix,int cur) {
        tot++;
        if (tot == n * m) {
            return;
        }

        for (int i = 0; i < 4; i++) {
            int xx = x + dir[(cur+i)%4][0], yy = y + dir[(cur+i)%4][1];
            if (tot == n * m) return;
            if (xx >= 0 && xx < n && yy >= 0 && yy < m && vis[xx][yy] == 0) {
                res.add(matrix[xx][yy]);
                vis[xx][yy] = 1;
                dfs(n, m, xx, yy, matrix,(cur+i)%4);
            }
        }
    }
}
