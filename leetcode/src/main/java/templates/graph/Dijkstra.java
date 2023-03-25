package templates.graph;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @description
 * @date 2023/3/25 19:25
 * @author: qyl
 */
public class Dijkstra {
    static int n, m, tot;

    static int[] dis, head;

    static class Edge {
        int to, w, next;
    }

    static Edge[] e;

    static void addEdge(int from, int to, int w) {
        e[tot].to = to;
        e[tot].w = w;
        e[tot].next = head[from];
        head[from] = tot++;
    }

    static PriorityQueue<Integer> q = new PriorityQueue<> (Comparator.comparingInt (i -> dis[i]));

    private static void dijkstra(int u) {
        dis[u] = 0;
        q.offer (u);
        while (!q.isEmpty ()) {
            u = q.poll ();
            for (int i = head[u]; i != -1; i = e[i].next) {
                int v = e[i].to, w = e[i].w;
                if (dis[v] > dis[u] + w) {
                    dis[v] = dis[u] + w;
                    q.offer (v);
                }
            }
        }
    }
}
