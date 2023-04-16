package daily;

import java.util.Scanner;

/**
 * @description 二维前缀和
 * @date 2023/4/16 17:28
 * @author: qyl
 */
public class Dp35 {
    public static void main(String[] args) {
        Scanner in = new Scanner (System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n = in.nextInt (), m = in.nextInt (), q = in.nextInt ();
        long[][] sum = new long[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                sum[i][j] = in.nextLong () + sum[i][j - 1];
            }
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                sum[i][j] += sum[i - 1][j];
            }
        }
        for (int i = 0; i < q; i++) {
            int x1 = in.nextInt (), y1 = in.nextInt (), x2 = in.nextInt (), y2 = in.nextInt ();
            System.out.println (sum[x2][y2] - sum[x2][y1 - 1] - sum[x1 - 1][y2] + sum[x1 - 1][y1 - 1]);
        }
    }
}
