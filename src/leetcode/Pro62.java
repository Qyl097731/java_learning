package leetcode;

/**
 * projectName:  fushi
 * packageName: leetcode
 * date: 2022-03-29 11:06
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Pro62 {
}
class Solution {
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m+1][n+1];
        dp[1][1] = 1;
        for(int i = 1 ; i <= m ; i++){
            for(int j = 1; j <= n;j++){
                dp[i][j] += dp[i-1][j] + dp[i][j-1];
            }
        }

        return dp[m][n];
    }
}
