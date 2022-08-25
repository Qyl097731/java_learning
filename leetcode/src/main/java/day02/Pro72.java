package day02;

/**
 * @author qyl
 * @program Pro72.java
 * @Description dp
 * @createTime 2022-08-25 16:09
 */
public class Pro72 {
    public static void main(String[] args) {
        System.out.println(new Solution72().minDistance("intention", "execution"));
    }
}

class Solution72 {
    public int minDistance(String word1, String word2) {
        int len1 = word1.length(), len2 = word2.length();
        if (len1 == 0 || len2 == 0){
            return len1 + len2;
        }
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0 ; i <= len1; i++){
            dp[i][0] = i;
        }

        for (int i = 0; i <= len2; i++) {
            dp[0][i] = i;
        }

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                dp[i][j] =  Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1);
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = Math.min(dp[i - 1][j - 1],dp[i][j]);
                }else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1] + 1, dp[i][j]);
                }
            }
        }
        return dp[len1][len2];
    }
}