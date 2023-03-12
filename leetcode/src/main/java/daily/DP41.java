package daily;

import java.io.*;
import java.util.StringTokenizer;

import static java.lang.System.in;
import static java.lang.System.out;

/**
 * @author asus
 */
public class DP41 {
    static FastScanner cin;
    static PrintWriter cout;

    private static void init() throws IOException {
        cin = new FastScanner (in);
        cout = new PrintWriter (out);
    }

    private static void close() {
        cout.close ();
    }

    public static void main(String[] args) throws IOException {
        init ();
        solve ();
        close ();
    }

    private static void solve() throws IOException {
        int n = cin.nextInt (), v = cin.nextInt ();
        int[][] items = new int[n][2];
        for (int i = 0; i < n; i++) {
            items[i][0] = cin.nextInt ();
            items[i][1] = cin.nextInt ();
        }
        int[][] dp = new int[v + 1][2];
        for (int i = 0; i <= v; i++) {
            dp[i][0] = dp[i][1] = Integer.MIN_VALUE;
        }
        dp[0][0] = 0;
        for (int[] item : items) {
            int vi = item[0], wi = item[1];
            for (int j = v; j >= vi; j--) {
                dp[j][0] = Math.max (dp[j][0], dp[j - vi][0] + wi);
                dp[j][1] = Math.max (dp[j][1], dp[j - vi][1] + wi);
            }
        }
        cout.println (dp[v][0]);
        if (dp[v][1] < 0) {
            dp[v][1] = 0;
        }
        cout.println (dp[v][1]);
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
    }

}
