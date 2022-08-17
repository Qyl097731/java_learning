package leetcode;

/**
 * projectName:  fushi
 * packageName: leetcode
 * date: 2022-03-29 13:49
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Pro64 {
}
class Solution64 {
    public int minPathSum(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][] dp = new int[n][m];
        for(int i = 0 ; i < n;i++){
            for(int j = 0 ; j < m;j++){
                dp[i][j] = grid[i][j];
                if(i - 1 >= 0 && j - 1 >= 0){
                    dp[i][j] += Math.min(dp[i][j-1],dp[i-1][j]) ;
                }else if(i - 1 >= 0){
                    dp[i][j] += dp[i-1][j];
                }else if(j - 1 >= 0){
                    dp[i][j] += dp[i][j-1];
                }
                System.out.println("i="+i+" j="+j+"dp"+dp[i][j]);
            }
        }
        return dp[n-1][m-1];
    }
}
