package daily;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @description
 * @date:2022/12/13 12:14
 * @author: qyl
 */
public class Solution200 {
    int[][] dir = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int numIslands(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == '1') {
                    cnt++;
                    Queue<int[]> queue = new ArrayDeque<> ( );
                    queue.add (new int[]{i, j});
                    while (!queue.isEmpty ( )) {
                        int[] front = queue.poll ( );
                        int x = front[0], y = front[1];
                        for (int k = 0; k < 4; k++) {
                            int xx = x + dir[k][0], yy = y + dir[k][1];
                            if (xx >= 0 && xx < n && yy >= 0 && yy < m && grid[xx][yy] == '1') {
                                queue.add (new int[]{xx, yy});
                                grid[xx][yy] = '0';
                            }
                        }
                    }
                }
            }
        }
        return cnt;
    }
}
