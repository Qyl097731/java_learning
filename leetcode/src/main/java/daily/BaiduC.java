package daily;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

import static java.lang.System.in;
import static java.lang.System.out;

/**
 * @author asus
 */
public class BaiduC {

    static final int INF = Integer.MAX_VALUE;
    static ArrayList<Integer>[] map;

    static int[] dp;

    static char[] color;

    static int maxN = 0;
    static FastScanner cin;
    static PrintWriter cout;

    public static void main(String[] args) throws IOException {
        init ();
        solve ();
        close ();
    }

    private static void solve() throws IOException {
        int n = cin.nextInt ();
        color = cin.next ().toCharArray ();
        map = new ArrayList[n + 1];
        Arrays.setAll (map, i -> new ArrayList<> ());
        for (int i = 0; i < n - 1; i++) {
            int u = cin.nextInt ();
            int v = cin.nextInt ();
            map[u].add (v);
            map[v].add (u);
        }
        dp = new int[n + 1];
        getDp (0, 1);
        long result = 0;
        for (int u = 1; u <= n; u++) {
            for (int j = 0; j < map[u].size (); j++) {
                int v = map[u].get (j);
                if (color[v - 1] == color[u - 1]) {
                    int part1 = maxN - Math.min (dp[u], dp[v]) + 1;
                    int part2 = Math.min (dp[u], dp[v]);
                    result += Math.abs (part2 - part1);
                } else {
                    int part1 = maxN - Math.min (dp[u], dp[v]);
                    int part2 = Math.min (dp[u], dp[v]);
                    result += Math.abs (part2 - part1);
                }
            }
        }
        cout.println (result / 2);
    }

    private static int getDp(int fa, int cur) {
        dp[cur] = 1;
        for (int i = 0; i < map[cur].size (); i++) {
            int son = map[cur].get (i);
            if (son != fa) {
                if (color[cur - 1] == color[son - 1]) {
                    dp[cur] += getDp (cur, son) - 1;
                } else {
                    dp[cur] += getDp (cur, son);
                }
            }
        }
        maxN = Math.max (dp[cur], maxN);
        return dp[cur];
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

