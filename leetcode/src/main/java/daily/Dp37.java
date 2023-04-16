package daily;

import java.util.Scanner;

/**
 * @description
 * @date 2023/4/16 18:02
 * @author: qyl
 */
public class Dp37 {
    public static void main(String[] args) {
        Scanner in = new Scanner (System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n = in.nextInt (), m = in.nextInt ();
        long[] sum = new long[n + 2];
        for (int i = 1; i <= n; i++) {
            int k = in.nextInt ();
            sum[i] += k;
            sum[i + 1] -= k;
        }

        for (int i = 0; i < m; i++) {
            int l = in.nextInt (), r = in.nextInt ();
            long k = in.nextLong ();
            sum[l] += k;
            sum[r + 1] -= k;
        }

        for (int i = 1; i <= n; i++) {
            sum[i] += sum[i - 1];
            System.out.print (sum[i] + " ");
        }
    }
}
