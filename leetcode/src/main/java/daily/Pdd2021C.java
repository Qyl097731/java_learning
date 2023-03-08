package daily;

import java.io.*;

/**
 * @description
 * @date 2023/3/8 23:54
 * @author: qyl
 */
public class Pdd2021C {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader (new InputStreamReader (System.in));
        BufferedWriter out = new BufferedWriter (new OutputStreamWriter (System.out));
        String[] input = in.readLine ().split (" ");

        int n = Integer.parseInt (input[0]);
        int m = Integer.parseInt (input[1]);
        String[] numbers = in.readLine ().split (" ");

        long[] preSum = new long[n + 1];
        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt (numbers[i]);
        }

        int result = 0;
        for (int i = 0; i < n; i++) {
            preSum[i + 1] = preSum[i] + a[i];
        }
        for (int len = 1; len < n; len++) {
            for (int start = 0; start + len <= n; start++) {
                if ((preSum[start + len] - preSum[start]) % m == 0) {
                    result++;
                }
            }
        }
        out.write (result + "\n");
        out.flush ();
    }
}
