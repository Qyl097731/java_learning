package day14;

/**
 * @description
 * @date:2022/10/4 8:30
 * @author: qyl
 */
public class Pro44 {
    public static void main(String[] args) {
        System.out.println(new Solution44().isMatch("caa", "?*"));
    }
}

class Solution44 {
    public boolean isMatch(String s, String p) {
        int lens = s.length(), lenp = p.length();
        boolean[][] dp = new boolean[lens + 1][lenp + 1];
        dp[0][0] = true;

        for (int i = 1; i <= lenp; i++) {
            if (p.charAt(i - 1) == '*') {
                dp[0][i] = dp[0][i - 1];
            } else {
                dp[0][i] = false;
            }
        }
        for (int i = 1; i <= lens; i++) {
            for (int j = 1; j <= lenp; j++) {
                if (p.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i - 1][j - 1] | dp[i - 1][j] | dp[i][j - 1];
                } else if (p.charAt(j - 1) == '?') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    if (s.charAt(i - 1) != p.charAt(j - 1)) {
                        dp[i][j] = false;
                    } else {
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                }
            }
        }
        return dp[lens][lenp];
    }
}
