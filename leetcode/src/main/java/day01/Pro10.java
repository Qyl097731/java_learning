package day01;

/**
 * @author qyl
 * @program Pro10.java
 * @Description TODO
 * @createTime 2022-08-24 14:53
 */
public class Pro10 {
    public static void main(String[] args) {
        String s = "aa";
        String p = "a*";
        System.out.println(new Solution10().isMatch(s, p));
    }
}

class Solution10 {
    public boolean isMatch(String s, String p) {
        int lens = s.length(), lenp = p.length();
        boolean[][] dp = new boolean[lens + 1][lenp + 1];
        dp[0][0] = true;
        for (int i = 0; i <= lens; i++) {
            for (int j = 1; j <= lenp; j++) {
                if (match(s, p, i, j)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    if (p.charAt(j - 1) == '*') {
                        dp[i][j] = dp[i][j - 2];
                        if (match(s, p, i, j - 1)) {
                            dp[i][j] = dp[i][j] || dp[i - 1][j];
                        }
                    }
                }
            }
        }
        return dp[lens][lenp];
    }

    boolean match(String s, String p, int i, int j) {
        return i >= 1 && (p.charAt(j - 1) == '.' || s.charAt(i - 1) == p.charAt(j - 1));
    }
}