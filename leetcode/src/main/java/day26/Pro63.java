package day26;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/10/18 12:43
 * @author: qyl
 */
class Solution63 {
    @Test
        public void test(){
        System.out.println(uniquePathsWithObstacles(new int[][]{{1}}));
    }

    int[][] dir = new int[][]{{1, 0}, {0, 1}};
    int[][] dp;
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int n = obstacleGrid.length,m = obstacleGrid[0].length;
        dp = new int[n][m];
        if (obstacleGrid[0][0] == 1){
            return 0;
        }
        return dfs(0, 0, n - 1,m-1, obstacleGrid);
    }

    private int dfs(int x, int y, int n,int m, int[][] g) {
        if (x == n && y == m) {
             dp[x][y] = 1^g[x][y];
            return dp[x][y];
        }

        if (dp[x][y] > 0) {
            return dp[x][y];
        }
        for (int i = 0; i < 2; i++) {
            int xx = x + dir[i][0], yy = y + dir[i][1];
            if (xx >= 0 && xx <= n && yy >= 0 && yy <= m && g[xx][yy] == 0) {
                dp[x][y] += dfs(xx, yy, n,m,g);
            }
        }
        return dp[x][y];
    }
}
