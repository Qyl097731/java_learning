package leetcode;

/**
 * projectName:  fushi
 * packageName: leetcode
 * date: 2022-03-29 11:40
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Pro70 {
}
class Solution70 {
    public int climbStairs(int n) {
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = 1;
        for(int i = 2 ; i < n+1 ;i++){
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
    }
}
