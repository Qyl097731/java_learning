package day20;

import java.util.Arrays;

/**
 * @description
 * @date:2022/10/11 18:53
 * @author: qyl
 */


public class Pro2435 {
    public static void main(String[] args) {
        System.out.println(new Solution2435().numberOfPaths(new int[][]{{7, 3, 4, 9}, {2, 3, 6, 2}, {2, 3, 7, 0}}, 1));
    }
}

class Solution2435 {
    int mod = (int) 1e9 + 7;
    int[][][] dp;
    int[][] dir = new int[][]{{0, 1}, {1, 0}};
    boolean[][] used;

    public int numberOfPaths(int[][] grid, int k) {
        int row = grid.length, col = grid[0].length;
        dp = new int[row + 1][col + 1][k];
        used = new boolean[row][col];
        for (int i = 0; i < row + 1; i++) {
            for (int j = 0; j < col + 1; j++) {
                for (int l = 0; l < k; l++) {
                    dp[i][j][l] = 0;
                }
            }
        }
        dfs(0, 0, row, col, grid, k);
        return dp[0][0][0] % mod;
    }

    private void dfs(int nr, int nl, int row, int col, int[][] grid, int k) {

        if (nr == row - 1 && nl == col - 1) {
            dp[nr][nl][grid[nr][nl] % k] = 1;
            return;
        }

        for (int i = 0; i < 2; i++) {
            int x = nr + dir[i][0];
            int y = nl + dir[i][1];
            if (x < row && y < col && x >= 0 && y >= 0 && !used[x][y]) {
                if (!used[x][y]) {
                    dfs(x, y, row, col, grid, k);
                    used[x][y] = true;
                }
            }
            if (x < row && y < col && x >= 0 && y >= 0) {
                for (int j = 0; j < k; j++) {
                    dp[nr][nl][(grid[nr][nl]+j) % k] = (dp[nr][nl][(grid[nr][nl]+j) % k] + dp[x][y][j]) % mod;
                }
            }
        }
    }
}

