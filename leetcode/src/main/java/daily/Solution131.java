package daily;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description
 * @date 2023/2/17 13:49
 * @author: qyl
 */
public class Solution131 {
    List<List<String>> res = new ArrayList<> ();
    List<String> ans = new ArrayList<> ();
    boolean[][] dp;

    public List<List<String>> partition(String s) {
        int n = s.length ();
        dp = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill (dp[i], true);
        }
        for (int i = n - 1; i > 0; i--) {
            for (int j = i + 1; j < n; j++) {
                dp[i][j] = (dp[i + 1][j - 1] && (s.charAt (i) == s.charAt (j)));
            }
        }
        dfs (s, 0, n);
        return res;
    }

    private void dfs(String s, int id, int n) {
        if (id == n) {
            res.add (new ArrayList<> (ans));
            return;
        }

        for (int i = id; i < s.length (); i++) {
            if (dp[id][i]) {
                ans.add (s.substring (id, i + 1));
                dfs (s, i + 1, n);
                ans.remove (ans.size () - 1);
            }
        }
    }

}
