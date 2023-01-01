package daily;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @description
 * @date:2023/1/1 20:14
 * @author: qyl
 */
public class Offer13 {
    int[][] dir = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public int movingCount(int m, int n, int k) {
//        return solve1(m,n,k);
        return solve2 (m, n, k);
    }

    private int solve2(int m, int n, int k) {
        int res = 0;
        boolean[][] vis = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (!smallThanK (i, j, k)) {
                    vis[i][j] = false;
                }
                if (i >= 1) {
                    vis[i][j] |= vis[i - 1][j];
                }
                if (j >= 1) {
                    vis[i][j] |= vis[i][j - 1];
                }
                res += vis[i][j] ? 1 : 0;
            }
        }
        return res;
    }

    private int solve1(int m, int n, int k) {
        boolean[][] vis = new boolean[m][n];
        int res = 0;
        Queue<int[]> q = new ArrayDeque<> ();
        q.offer (new int[]{0, 0});
        vis[0][0] = true;
        while (!q.isEmpty ()) {
            int[] front = q.poll ();
            int x = front[0], y = front[1];
            res++;
            for (int i = 0; i < 4; i++) {
                int xx = x + dir[i][0], yy = y + dir[i][1];
                if (xx >= 0 && xx <= m - 1 && yy >= 0 && yy <= n - 1 && !vis[xx][yy] && smallThanK (xx, yy, k)) {
                    vis[xx][yy] = true;
                    q.offer (new int[]{xx, yy});
                }
            }
        }
        return res;
    }

    private boolean smallThanK(int x, int y, int k) {
        int num = 0;
        while (x > 0) {
            num += x % 10;
            x /= 10;
        }
        while (y > 0) {
            num += y % 10;
            y /= 10;
        }
        return num <= k;
    }
}
