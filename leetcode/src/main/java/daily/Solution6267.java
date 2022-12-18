package daily;

import java.util.*;

/**
 * @description
 * @date:2022/12/18 17:30
 * @author: qyl
 */
public class Solution6267 {
    public boolean isPossible(int n, List<List<Integer>> edges) {
        int[] in = new int[n + 1];
        Set<Integer>[] g = new HashSet[n + 1];
        Arrays.setAll (g, e -> new HashSet<Integer> ( ));
        for (List<Integer> edge : edges) {
            int u = edge.get (0), v = edge.get (1);
            in[u]++;
            in[v]++;
            g[u].add (v);
            g[v].add (u);
        }
        int tot = 0;
        List<Integer> pts = new ArrayList<> ( );
        for (int i = 1; i <= n; i++) {
            if (in[i] % 2 != 0) {
                tot++;
                pts.add (i);
            }
        }
        if (tot == 0) return true;
        if (tot == 2) {
            // 相连
            if (g[pts.get (0)].contains (pts.get (1))) {
                for (int i = 1; i <= n; i++) {
                    if (i != pts.get (0) && i != pts.get (1) && !g[pts.get (0)].contains (i) && !g[pts.get (1)].contains (i)) {
                        return true;
                    }
                }
            } else {
                return true;
            }
        }
        if (tot == 4) {
            int a = pts.get (0), b = pts.get (1), c = pts.get (2), d = pts.get (3);
            return !g[a].contains (b) && !g[c].contains (d)
                    || !g[a].contains (c) && !g[b].contains (d)
                    || !g[a].contains (d) && !g[c].contains (b);
        }
        return false;
    }
}
