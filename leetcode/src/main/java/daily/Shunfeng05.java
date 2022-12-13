package daily;

/**
 * @description
 * @date:2022/12/13 11:57
 * @author: qyl
 */
public class Shunfeng05 {
    int[] root = new int[2000], h = new int[2000];

    void init() {
        for (int i = 0; i < 2000; i++) {
            root[i] = i;
            h[i] = 1;
        }
    }

    void merge(int u, int v) {
        u = find (u);
        v = find (v);
        if (u != v) {
            if (h[u] < h[v]) {
                root[u] = v;
            } else {
                root[v] = u;
                h[u] += h[u] == h[v] ? 1 : 0;
            }
        }
    }

    int find(int u) {
        return root[u] = root[u] == u ? root[u] : find (root[u]);
    }

    public boolean isCompliance(int[][] distance, int n) {
        int m = distance.length;
        init ( );
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                if (distance[i][j] <= 2) {
                    merge (i, j);
                }
            }
        }
        for (int i = 0; i < m; i++) {
            if (find (i) == i) {
                n--;
            }
        }
        return n >= 0;
    }
}
