package templates;

import java.io.*;
import java.util.StringTokenizer;

import static java.lang.System.in;
import static java.lang.System.out;

/**
 * @author asus
 */
public class DP34 {
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
        int n = cin.nextInt (), q = cin.nextInt ();
        long[] preSum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            preSum[i + 1] = cin.nextLong ();
            preSum[i + 1] += preSum[i];
        }
        for (int i = 0; i < q; i++) {
            int l = cin.nextInt (), r = cin.nextInt ();
            cout.println (preSum[r] - preSum[l - 1]);
        }
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st = new StringTokenizer ("");

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

