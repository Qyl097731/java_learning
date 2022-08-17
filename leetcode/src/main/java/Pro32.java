package leetcode;

/**
 * projectName:  fushi
 * packageName: leetcode
 * date: 2022-03-28 15:16
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Pro32 {
    public static void main(String[] args) {
        new Solution32().
                longestValidParentheses(new String("()(())"));
    }
}

class Solution32 {
    public int longestValidParentheses(String s) {
        int[] dp = new int[s.length()];
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ')' && i >= 1) {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                }
                int idx = i - dp[i - 1] - 1;
                if (idx >= 0) {
                    if (s.charAt(idx) == '(') {
                        dp[i] = Math.max(dp[i], dp[i-1] + 2 + (idx - 1 >= 0 ? dp[idx - 1] : 0));
                    }
                }
            } else {
                dp[i] = 0;
            }
            res = Math.max(res, dp[i]);
        }
        return res;
    }
}
