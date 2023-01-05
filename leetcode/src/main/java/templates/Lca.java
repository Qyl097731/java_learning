package templates;

import common.TreeNode;

import java.util.Arrays;

/**
 * @description
 * @date:2023/1/5 18:14
 * @author: qyl
 */
public class Lca {
    static final int N = 100005;
    static Edge[] edges = new Edge[N];
    static int[] head = new int[N], dep = new int[N];
    static int[][] fa = new int[N][21];

    static {
        Arrays.fill (head, -1);
        for (int i = 0; i < N; i++) {
            Arrays.fill (fa[i], 0);
            edges[i] = new Edge ();
        }
    }

    int tot;
    TreeNode res = null;

    void addEdge(int u, int v) {
        edges[++tot].v = v;
        edges[tot].nex = head[u];
        head[u] = tot;
    }

    void dfs(int u, int f) {
        dep[u] = dep[f] + 1;
        fa[u][0] = f;
        for (int i = 1; i < 21; i++) {
            fa[u][i] = fa[fa[u][i - 1]][i - 1];
        }
        for (int i = head[u]; i != -1; i = edges[i].nex) {
            int v = edges[i].v;
            if (v != f) {
                dfs (v, u);
            }
        }
    }

    int lca(int a, int b) {
        if (dep[a] < dep[b]) {
            int temp = a;
            a = b;
            b = temp;
        }
        int df = dep[a] - dep[b];
        for (int i = 0; i < 21; i++) {
            if ((df & (1 << i)) == 1) {
                a = fa[a][i];
            }
        }
        if (a == b) {
            return a;
        }
        for (int i = 20; i >= 0; i--) {
            if (fa[a][i] != fa[b][i]) {
                a = fa[a][i];
                b = fa[b][i];
            }
        }
        return fa[a][0];
    }

    static class Edge {
        int v;
        int nex;
    }
}
