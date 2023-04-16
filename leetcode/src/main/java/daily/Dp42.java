package daily;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @description
 * @date 2023/4/16 23:00
 * @author: qyl
 */
public class Dp42 {
    public static void main(String[] args) {
        Scanner in = new Scanner (System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n = in.nextInt (), v = in.nextInt ();
        int[] dp = new int[v + 1];
        int[][] goods = new int[n][2];
        for (int i = 0; i < n; i++) {
            goods[i][0] = in.nextInt ();
            goods[i][1] = in.nextInt ();
        }
        for (int i = 0; i < n; i++) {
            for (int j = goods[i][0]; j <= v; j++) {
                dp[j] = Math.max (dp[j], dp[j - goods[i][0]] + goods[i][1]);
            }
        }
        int[] dp2 = new int[v + 1];
        Arrays.fill (dp2, Integer.MIN_VALUE);
        dp2[0] = 0;
        for (int i = 0; i < n; i++) {
            for (int j = goods[i][0]; j <= v; j++) {
                dp2[j] = Math.max (dp2[j], dp2[j - goods[i][0]] + goods[i][1]);
            }
        }
        System.out.print (dp[v] + "\n" + (dp2[v] < 0 ? 0 : dp2[v]));
    }
}
