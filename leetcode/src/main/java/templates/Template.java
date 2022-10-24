package templates;

import java.util.*;
import java.io.*;

import static java.lang.System.*;

/**
 * @author asus
 */
public class Template {
    static FastScanner cin;
    static PrintWriter cout;

    private static void init() throws IOException {
        cin = new FastScanner(in);
        cout = new PrintWriter(out);
    }

    private static void close() {
        cout.close();
    }

    public static void main(String[] args) throws IOException {
        init();
        solve();
        close();
    }

    private static void solve() throws IOException {

    }
}

class FastScanner {
    BufferedReader br;
    StringTokenizer st = new StringTokenizer("");

    public FastScanner(InputStream s) {
        br = new BufferedReader(new InputStreamReader(s));
    }

    public FastScanner(String s) throws FileNotFoundException {
        br = new BufferedReader(new FileReader(new File(s)));
    }

    public String next() throws IOException {
        while (!st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    public long nextLong() throws IOException {
        return Long.parseLong(next());
    }

    public double nextDouble() throws IOException {
        return Double.parseDouble(next());
    }
}
