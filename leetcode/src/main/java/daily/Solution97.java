package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date 2023/2/11 23:53
 * @author: qyl
 */
public class Solution97 {
    public boolean isInterleave(String s1, String s2, String s3) {
//        return solve1(s1,s2,s3);
        return solve2 (s1, s2, s3);
    }

    private boolean solve2(String s1, String s2, String s3) {
        int n1 = s1.length (), n2 = s2.length (), n3 = s3.length ();
        if (n1 + n2 != n3) {
            return false;
        }
        boolean[] dp = new boolean[n1 + 1];
        dp[0] = true;
        for (int i = 1; i <= n3; i++) {
            for (int j = n1; j >= 0; j--) {
                int k = i - j;
                if (k <= n2 && k >= 0) {
                    if (k > 0) {
                        dp[j] &= (s3.charAt (i - 1) == s2.charAt (k - 1));
                    }
                    if (j > 0 && s3.charAt (i - 1) == s1.charAt (j - 1)) {
                        dp[j] |= dp[j - 1];
                    }
                }
            }
        }
        return dp[n1];
    }

    private boolean solve1(String s1, String s2, String s3) {
        int n1 = s1.length (), n2 = s2.length (), n3 = s3.length ();
        if (n1 + n2 != n3) {
            return false;
        }
        boolean[][] dp = new boolean[n3 + 1][n1 + 1];
        dp[0][0] = true;
        for (int i = 1; i <= n3; i++) {
            for (int j = 0; j <= i; j++) {
                int k = i - j;
                if (k <= n2 && j <= n1) {
                    if (j > 0 && s3.charAt (i - 1) == s1.charAt (j - 1)) {
                        dp[i][j] |= dp[i - 1][j - 1];
                    }
                    if (k > 0 && s3.charAt (i - 1) == s2.charAt (k - 1)) {
                        dp[i][j] |= dp[i - 1][j];
                    }
                }
            }
        }
        return dp[n3][n1];
    }

    @Test
    public void test() {
        isInterleave ("aabcc", "dbbca", "aadbbcbcac");
    }
}
