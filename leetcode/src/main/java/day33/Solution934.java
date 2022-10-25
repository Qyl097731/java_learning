package day33;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @description
 * @date:2022/10/25 7:57
 * @author: qyl
 */
public class Solution934 {
    @Test
    public void test() {
        System.out.println(shortestBridge(new int[][]{{1, 1, 1, 1, 1}, {1, 0, 0, 0, 1}, {1, 0, 1, 0, 1}, {1, 0, 0, 0, 1}, {1, 1, 1, 1, 1}}));
    }


    public int shortestBridge(int[][] grid) {
        int n, m;
        int res = 0;
        int[][] dis = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        Queue<int[]> queue = new ArrayDeque<>();
        List<int[]> islands = new ArrayList<>();
        n = grid.length;
        m = grid[0].length;
        for (int ix = 0; ix < n; ix++) {
            for (int jy = 0; jy < m; jy++) {
                if (grid[ix][jy] == 1) {
                    queue.add(new int[]{ix, jy});
                    grid[ix][jy] = -1;

                    while (!queue.isEmpty()) {
                        int[] head = queue.poll();
                        islands.add(head);
                        for (int i = 0; i < 4; i++) {
                            int x = head[0] + dis[i][0], y = head[1] + dis[i][1];
                            if (x >= 0 && x < n && y >= 0 && y < m && grid[x][y] == 1) {
                                queue.add(new int[]{x, y});
                                grid[x][y] = -1;
                            }
                        }
                    }

                    queue.addAll(islands);

                    while (!queue.isEmpty()) {
                        int sz = queue.size();
                        for (int ik = 0; ik < sz; ik++) {
                            int[] head = queue.poll();
                            for (int i = 0; i < 4; i++) {
                                int x = head[0] + dis[i][0], y = head[1] + dis[i][1];
                                if (x >= 0 && x < n && y >= 0 && y < m) {
                                    if (grid[x][y] == 0) {
                                        queue.add(new int[]{x, y});
                                        grid[head[0]][head[1]] = -1;
                                    } else if (grid[x][y] == 1) {
                                        return res;
                                    }
                                }
                            }
                        }
                        res++;
                    }
                }
            }
        }

        return res;
    }
}
