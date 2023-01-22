package daily;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description
 * @date:2023/1/22 19:00
 * @author: qyl
 */
public class Solution2538 {
    int[] price;
    long ans;
    List<Integer>[] g;

    public long maxOutput(int n, int[][] edges, int[] price) {
        this.price = price;
        g = new ArrayList[n];
        Arrays.setAll (g, e -> new ArrayList<Integer> ());
        for (int[] e : edges) {
            int x = e[0], y = e[1];
            g[x].add (y);
            g[y].add (x);
        }
        dfs (0, -1);
        return ans;
    }

    private long[] dfs(int x, int fa) {
        long p = price[x], maxS1 = p, maxS2 = 0;
        for (int to : g[x]) {
            if (to != fa) {
                long[] res = dfs (to, x);
                long s1 = res[0], s2 = res[1];
                ans = Math.max (ans, Math.max (maxS1 + s2, maxS2 + s1));
                maxS1 = Math.max (maxS1, s1 + p);
                maxS2 = Math.max (maxS2, s2 + p);
            }
        }
        return new long[]{maxS1, maxS2};
    }
}
