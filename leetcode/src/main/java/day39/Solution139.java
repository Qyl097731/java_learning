package day39;

import java.util.Arrays;
import java.util.List;

/**
 * @description
 * @date:2022/11/4 19:06
 * @author: qyl
 */
public class Solution139 {
    public boolean wordBreak(String s, List<String> wordDict) {
        int n = s.length(), m = wordDict.size();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < m; j++) {
                int start = i - wordDict.get(j).length();
                if (start >= 0 && wordDict.get(j).equals(s.substring(start, i))) {
                    dp[i] |= dp[start] ;
                }
            }
        }
        return dp[n];
    }
}
