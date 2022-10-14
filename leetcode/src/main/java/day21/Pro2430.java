package day21;

import java.util.Arrays;

/**
 * @description
 * @date:2022/10/12 21:20
 * @author: qyl
 */
public class Pro2430 {
    public static void main(String[] args) {
        System.out.println(new Solution2430().deleteString("aaabaab"));
    }
}

class Solution2430 {
    public int deleteString(String s) {
        int len = s.length();
        int[][] lcp = new int[len+1][len+1];
        int[] dp = new int[len+1];

        for (int i = len-1; i >= 0; i--) {
            for (int j = len-1; j >= 0 ; j--) {
                if (s.charAt(i) != s.charAt(j)) {
                    lcp[i][j] = 0;
                }else{
                    lcp[i][j] = lcp[i+1][j+1] + 1;
                }
            }
        }

        for (int i = len-1; i >= 0; i--) {
            dp[i] = 1;
            for (int j = 1; i + 2 * j <= len ; j++) {
                if (lcp[i][j+i] >= j){
                    dp[i] = Math.max(dp[i],dp[i+j]+1);
                }
            }
        }
        return dp[0];
    }
}
