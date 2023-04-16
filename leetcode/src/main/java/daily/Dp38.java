package daily;

import java.util.Scanner;

/**
 * @description
 * @date 2023/4/16 22:29
 * @author: qyl
 */
public class Dp38 {
    public static void main(String[] args) {
        Scanner in = new Scanner (System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n = in.nextInt (), m = in.nextInt (), q = in.nextInt ();
        long[][] sum = new long[n + 2][m + 2];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int k = in.nextInt ();
                sum[i][j] += k;
                sum[i][j + 1] -= k;
                sum[i + 1][j] -= k;
                sum[i + 1][j + 1] += k;
            }
        }
        for (int i = 0; i < q; i++) {
            int x1 = in.nextInt (), y1 = in.nextInt (), x2 = in.nextInt (), y2 = in.nextInt ();
            long k = in.nextLong ();
            sum[x1][y1] += k;
            sum[x1][y2 + 1] -= k;
            sum[x2 + 1][y1] -= k;
            sum[x2 + 1][y2 + 1] += k;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                sum[i][j] += sum[i][j - 1];
            }
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                sum[i][j] += sum[i - 1][j];
                System.out.print (sum[i][j] + " ");
            }
            System.out.println ();
        }
    }
}
