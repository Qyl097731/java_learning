package daily;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

import static java.lang.System.in;
import static java.lang.System.out;

/**
 * @description
 * @date 2023/3/25 19:42
 * @author: qyl
 */
public class Poj2387 {
    static FastScanner cin;
    static PrintWriter cout;

    public static void main(String[] args) throws IOException {
        init ();
        solve ();
        close ();
    }

    static int t, n;
    static int[] dis;
    static int[][] graph;

    static final int INF = 0x3f3f3f3f;

    private static void solve() throws IOException {
        t = cin.nextInt ();
        n = cin.nextInt ();
        graph = new int[n + 1][n + 1];
        dis = new int[n + 1];
        Arrays.fill (dis, INF);
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                graph[i][j] = Integer.MAX_VALUE;
            }
        }
        for (int i = 0; i < t; i++) {
            int u = cin.nextInt (), v = cin.nextInt ();
            graph[v][u] = Math.min (cin.nextInt (), graph[v][u]);
            graph[u][v] = Math.min (graph[u][v], graph[v][u]);
        }
        dijkstra (n);
        cout.println (dis[1]);
    }

    private static void dijkstra(int u) {
        PriorityQueue<Integer> queue = new PriorityQueue<> (Comparator.comparingInt (i -> dis[i]));
        dis[u] = 0;
        queue.offer (u);
        while (!queue.isEmpty ()) {
            int point = queue.poll ();
            for (int i = 1; i <= n; i++) {
                if (dis[i] - dis[point]>  graph[point][i]){
                    dis[i] = dis[point] + graph[point][i];
                    queue.offer (i);
                }
            }
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
