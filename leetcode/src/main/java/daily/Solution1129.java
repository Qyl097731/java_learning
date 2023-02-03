package daily;

import java.util.*;

/**
 * @description
 * @date:2023/2/2 23:54
 * @author: qyl
 */
public class Solution1129 {
    public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
        List<Integer>[][] edges = new List[2][n];
        for (int i = 0; i < edges.length; i++) {
            for (int j = 0; j < n; j++) {
                edges[i][j] = new ArrayList<> ();
            }
        }
        for (int[] edge : redEdges) {
            edges[0][edge[0]].add (edge[1]);
        }
        for (int[] edge : blueEdges) {
            edges[1][edge[0]].add (edge[1]);
        }
        int[] ans = new int[n];
        int[][] dis = new int[2][n];
        Arrays.fill (dis[0], Integer.MAX_VALUE);
        Arrays.fill (dis[1], Integer.MAX_VALUE);
        dis[0][0] = dis[1][0] = 0;
        Queue<int[]> q = new ArrayDeque<> ();
        q.offer (new int[]{0, 0});
        q.offer (new int[]{1, 0});
        while (!q.isEmpty ()) {
            int[] pair = q.poll ();
            int type = pair[0], u = pair[1];
            for (int i = 0; i < edges[1 - type][u].size (); i++) {
                int v = edges[1 - type][u].get (i);
                if (dis[1 - type][v] > 1 + dis[type][u]) {
                    dis[1 - type][v] = 1 + dis[type][u];
                    q.offer (new int[]{1 - type, v});
                }
            }
        }
        for (int i = 0; i < n; i++) {
            ans[i] = Math.min (dis[0][i], dis[1][i]);
            ans[i] = ans[i] == Integer.MAX_VALUE ? -1 : ans[i];
        }
        return ans;
    }
}
