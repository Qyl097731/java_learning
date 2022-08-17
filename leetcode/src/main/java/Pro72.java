package leetcode;

import java.util.Arrays;

/**
 * projectName:  fushi
 * packageName: leetcode
 * date: 2022-03-29 14:43
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Pro72 {
    public static void main(String[] args) {
        new Solution72().minDistance("horse","ros");
    }
}
class Solution72 {
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[][] dp = new int[m+1][n+1];
        for (int i = 0 ; i < m;i++){
            for(int j = 0 ; j < n; j++){
                int ansLeft = (i-1>=0?dp[i-1][j]:0)+1;
                int ansRight = (j-1>=0?dp[i][j-1]:0)+1;
                int mid = ((j-1>=0&&i-1>=0)?dp[i-1][j-1]:0)+1;
                if(word1.charAt(i) == word2.charAt(j)) {
                    mid--;
                }
                dp[i][j] = Math.min(ansLeft,Math.min(ansRight,mid));
                System.out.print(dp[i][j]);
            }
            System.out.println();
        }
        return dp[m-1][n-1];
    }
}
