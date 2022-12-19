package daily;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.IntStream;

/**
 * @description
 * @date:2022/12/19 15:19
 * @author: qyl
 */
public class Solution1971 {
    boolean flag = false;

    public boolean validPath(int n, int[][] edges, int source, int destination) {
        List<Integer>[] g = new List[n];
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<> ( );
        }
        for (int[] edge : edges) {
            g[edge[0]].add (edge[1]);
            g[edge[1]].add (edge[0]);
        }
//        return solve1 (n, g, source, destination,visited);

//        return solve2 (n, g, source, destination,visited);
        return solve3 (n, edges, source, destination);
    }

    private boolean solve3(int n, int[][] edges, int source, int destination) {
        int[] r = IntStream.iterate (0, i -> i + 1).limit (n).toArray ( );
        int[] h = IntStream.generate (() -> 0).limit (n).toArray ( );
        for (int[] edge : edges) {
            merge (r, h, edge[0], edge[1]);
        }
        return find (source, r) == find (destination, r);
    }

    int find(int u, int[] r) {
        return r[u] = r[u] == u ? r[u] : find (r[u], r);
    }

    void merge(int[] r, int[] h, int u, int v) {
        u = find (u, r);
        v = find (v, r);
        if (u != v) {
            if (h[u] > h[v]) {
                r[v] = u;
            } else {
                r[u] = v;
                h[v] += (h[u] == h[v] ? 1 : 0);
            }
        }
    }

    private boolean solve2(int n, List<Integer>[] g, int source, int destination, boolean[] visited) {
        visited[source] = true;
        if (flag) {
            return true;
        }
        if (source == destination) {
            flag = true;
            return true;
        }

        for (int to : g[source]) {
            if (flag) {
                return true;
            }
            if (!visited[to]) {
                solve2 (n, g, to, destination, visited);
            }
        }
        return flag;
    }

    private boolean solve1(int n, List<Integer>[] g, int source, int destination, boolean[] visited) {
        Queue<Integer> queue = new ArrayDeque<> ( );
        queue.add (source);
        visited[source] = true;
        while (!queue.isEmpty ( )) {
            int u = queue.poll ( );
            if (u == destination) {
                return true;
            }
            for (int edge : g[u]) {
                if (!visited[edge]) {
                    queue.offer (edge);
                    visited[edge] = true;
                }
            }
        }
        return false;
    }


    @Test
    public void test() {
        validPath (6, new int[][]{{0, 1}, {0, 2}, {3, 5}, {5, 4}, {4, 3}}, 0, 5);
    }

}
