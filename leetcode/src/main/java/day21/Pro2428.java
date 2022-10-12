package day21;

/**
 * @description
 * @date:2022/10/12 20:35
 * @author: qyl
 */
class Solution2428 {
    public int maxSum(int[][] grid) {
        int res =0 ,temp = 0;
        int row = grid.length , col = grid[0].length;

        for (int i = 0; i+2 < row; i++) {
            for (int j = 0;j+2 < col; j++) {
                temp =
                        grid[i][j] + grid[i][j+1]+ grid[i][j+2] + grid[i+1][j+1] + grid[i+2][j]+grid[i+2][j+1]+grid[i+2][j+2];
                res = Math.max(temp,res);
            }
        }

        return res;
    }
}
