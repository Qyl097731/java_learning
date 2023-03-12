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
public class DP54 {
    static ArrayList<Integer>[] map;
    static char[] colors;
    static FastScanner cin;
    static PrintWriter cout;

    public static void main(String[] args) throws IOException {
        init ();
        solve ();
        close ();
    }

    private static void solve() throws IOException {
        int n = cin.nextInt ();
        map = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            map[i] = new ArrayList<> ();
        }
        for (int i = 2; i <= n; i++) {
            int parent = cin.nextInt ();
            map[parent].add (i);
        }
        colors = cin.next ().toCharArray ();
        int[] dp = new int[n + 1];
        Arrays.fill (dp, -1);
        dfs (0, dp);
        int q = cin.nextInt ();
        for (int i = 0; i < q; i++) {
            cout.println (dp[cin.nextInt ()]);
        }
    }

    private static int dfs(int root, int[] dp) {
        if (dp[root] != -1) {
            return dp[root];
        }
        for (int child : map[root]) {
            dp[root] += dfs (child, dp);
        }
        dp[root] += (colors[root] == 'R' ? 1 : 0);
        return dp[root];
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

