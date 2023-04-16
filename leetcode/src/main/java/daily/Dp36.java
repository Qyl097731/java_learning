package daily;

import java.util.Scanner;

/**
 * @description
 * @date 2023/4/16 17:41
 * @author: qyl
 */
public class Dp36 {
    public static void main(String[] args) {
        Scanner in = new Scanner (System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n = Integer.parseInt (in.nextLine ());
        char[] str = in.nextLine ().toCharArray ();
        long[][] sum = new long[n + 1][26];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < 26; j++) {
                sum[i][j] += sum[i + 1][j];
            }
            sum[i][str[i] - 'a']++;
        }

        long res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 26; j++) {
                if (j != str[i] - 'a') {
                    res += sum[i + 1][j] * (sum[i + 1][j] - 1) / 2;
                }
            }
        }
        System.out.println (res);
    }
}
