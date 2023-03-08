package daily;

import java.io.*;
import java.util.Arrays;

/**
 * @description
 * @date 2023/3/8 21:53
 * @author: qyl
 */
public class Pdd2021B {
    static int result = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader (new InputStreamReader (System.in));
        BufferedWriter out = new BufferedWriter (new OutputStreamWriter (System.out));

        int n = Integer.parseInt (in.readLine ());
        String x = in.readLine (), y = in.readLine ();
        int[][] count = new int[2][n];
        for (int i = 0; i < n; i++) {
            count[0][i] = x.charAt (i) - '0';
            count[1][i] = y.charAt (i) - '0';
        }
        Arrays.sort (count[0]);
        Arrays.sort (count[1]);
        for (int i = 0; i < n; i++) {
            result += Math.abs (count[0][i] - count[1][i]);
        }
        out.write (result + "\n");
        out.flush ();
    }
}
