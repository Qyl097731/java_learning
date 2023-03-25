package daily;

import java.io.*;
import java.util.*;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.System.in;
import static java.lang.System.out;

/**
 * @description
 * @date 2023/3/25 20:46
 * @author: qyl
 */
public class NC14369 {
    static FastScanner cin;
    static PrintWriter cout;

    public static void main(String[] args) throws IOException {
        init ();
        solve ();
        close ();
    }

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

    private static void solve() throws IOException {
        n = cin.nextInt ();
        m = cin.nextInt ();
        dis = new int[n + 1];
        head = new int[n + 1];
        cnt = new int[n + 1];
        vis = new boolean[n + 1];
        e = new Edge[m + 1];
        for (int i = 0; i <= n ; i++) {
            head[i] = -1;
            dis[i] = MAX_VALUE;
        }
        for (int i = 0; i < m; i++) {
            int u = cin.nextInt (), v = cin.nextInt (), w = cin.nextInt ();
            addEdge (u,v,w);
        }
        spfa (1);
        for (int i = 2; i <= n; i++) {
            cout.println (dis[i]);
        }
    }

    private static void init() throws IOException {
        cin = new FastScanner (in);
        cout = new PrintWriter (out);
    }

    private static void close() {
        cout.close ();
    }


    static class FastScanner {
        BufferedReader br;
        StringTokenizer st = new StringTokenizer (" ");

        public FastScanner(InputStream s) {
            br = new BufferedReader (new InputStreamReader (s));
        }

        public FastScanner(String s) throws FileNotFoundException {
            br = new BufferedReader (new FileReader (s));
        }

        public String next() throws IOException {
            while (!st.hasMoreTokens ()) {
                try {
                    st = new StringTokenizer (br.readLine ());
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
            return st.nextToken ();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt (next ());
        }

        public long nextLong() throws IOException {
            return Long.parseLong (next ());
        }

        public double nextDouble() throws IOException {
            return Double.parseDouble (next ());
        }

        public void setSt(String s) {
            st = new StringTokenizer (s);
        }
    }
}
