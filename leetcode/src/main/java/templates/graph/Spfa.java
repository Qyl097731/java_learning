package templates.graph;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @description
 * @date 2023/3/25 20:36
 * @author: qyl
 */
public class Spfa {
    static int n,m, tot;
    static int[] dis, cnt, head;
    static boolean[] vis;
    static final int INF = Integer.MAX_VALUE;

    static class Edge {
        int to, w, next;

        public Edge(int from, int to, int w) {
            this.to = to;
            this.w = w;
            this.next = head[from];
        }
    }

    static Edge[] e;

    static void addEdge(int from, int to, int w) {
        e[tot] = new Edge (from, to, w);
        head[from] = tot++;
    }


    static boolean spfa(int u) {
        PriorityQueue<Integer> queue = new PriorityQueue<> (Comparator.comparingInt (i -> dis[i]));
        vis[u] = true;
        dis[u] = 0;
        queue.offer (u);
        while (!queue.isEmpty ()) {
            u = queue.poll ();
            vis[u] = false;
            for (int i = head[u]; i != -1 ; i = e[i].next) {
                int v = e[i].to, w = e[i].w;
                if (dis[v] > dis[u] + w){
                    dis[v] = dis[u] + w;
                    if (!vis[v]){
                        queue.offer (v);
                        vis[v] = true;
                    }
                    if (++cnt[v] == n){
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
