package daily;

/**
 * @description
 * @date:2022/12/14 10:54
 * @author: qyl
 */
public class Solution2316 {
    int[] root = new int[100005], h = new int[100005];

    void init(int n) {
        for (int i = 0; i < n; i++) {
            root[i] = i;
            h[i] = 1;
        }
    }

    int find(int u) {
        return root[u] = root[u] == u ? root[u] : find (root[u]);
    }

    void merge(int u, int v) {
        u = find (u);
        v = find (v);
        if (u != v) {
            if (h[u] < h[v]) {
                root[u] = v;
                h[v] += h[u];
                h[u] = 0;
            } else {
                root[v] = u;
                h[u] += h[v];
                h[v] = 0;
            }
        }
    }

    public long countPairs(int n, int[][] edges) {
        init (n);
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            merge (u, v);
        }

        long res = (long) n * (n - 1) / 2;
        for (int i = 0; i < n; i++) {
            res -= (long) h[i] * (h[i] - 1) / 2;
        }

        return res;
    }
}
