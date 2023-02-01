package daily;

/**
 * @description
 * @date:2023/2/1 0:08
 * @author: qyl
 */
public class Solution91 {
    public int numDecodings(String s) {
        int n = s.length ();
        if (s.charAt (0) == '0') {
            return 0;
        }
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[0] = 1;
        for (int i = 1; i < n; i++) {
            if (s.charAt (i) != '0') {
                dp[i + 1] = dp[i];
            }
            int num = (s.charAt (i - 1) - '0') * 10 + (s.charAt (i) - '0');
            if (num <= 26 && num >= 1 && s.charAt (i - 1) != '0') {
                dp[i + 1] += dp[i - 1];
            }
        }
        return dp[n];
    }
}
