package daily;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * @description
 * @date:2022/12/8 17:01
 * @author: qyl
 */
public class Solution455A {
    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader (new InputStreamReader (System.in));
        PrintWriter pw = new PrintWriter (System.out);
        int n = Integer.parseInt (r.readLine ( ));
        long[] c = new long[100005];
        int[] arr = new int[n];
        StringTokenizer tokenizer = new StringTokenizer (r.readLine ( ));
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt (tokenizer.nextToken ( ));
            c[arr[i]]++;
        }

        long[] dp = new long[100005];

        dp[1] = c[1];
        for (int i = 2; i < dp.length; i++) {
            dp[i] = Math.max (dp[i - 1], dp[i - 2] + i * c[i]);
        }

        pw.println (dp[100004]);
        pw.flush ( );
        pw.close ( );
    }
}
