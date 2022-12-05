package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/12/5 12:22
 * @author: qyl
 */
public class Solution6255 {
    int[] fa = new int[100005], h = new int[100005];

    void init() {
        for (int i = 0; i < fa.length; i++) {
            fa[i] = i;
            h[i] = 0;
        }
    }

    int find(int u) {
        return fa[u] = fa[u] == u ? u : find (fa[u]);
    }

    void merge(int u, int v) {
        u = find (u);
        v = find (v);
        if (v != u) {
            if (h[u] > h[v]) {
                fa[v] = u;
            } else {
                fa[u] = v;
                h[v] += h[v] == h[u] ? 1 : 0;
            }
        }
    }

    public int minScore(int n, int[][] roads) {
        init ( );
        int m = roads.length;
        for (int i = 0; i < m; i++) {
            int u = roads[i][0], v = roads[i][1];
            merge (u, v);
        }

        int res = Integer.MAX_VALUE;
        int root = find (1);
        for (int i = 0; i < m; i++) {
            if (find (roads[i][0]) == root || find (roads[i][1]) == root) {
                res = Math.min (res, roads[i][2]);
            }
        }
        return res;

    }

    @Test
    public void test() {
        int[][] r = {{1, 2, 9}, {2, 3, 6}, {2, 4, 5}, {1, 4, 7}};
        minScore (4, r);
    }


}
