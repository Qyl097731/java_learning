package templates.dp;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @description
 * @date:2022/10/10 18:17
 * @author: qyl
 */
public class SuspensionDP {
    static int n, m;
    static int[][] map, l, r, up;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        m = scanner.nextInt();
        map = new int[2010][2010];
        l = new int[2010][2010];
        r = new int[2010][2010];
        up = new int[2010][2010];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                map[i][j] = Integer.parseInt(scanner.next());
                l[i][j] = r[i][j] = j;
                up[i][j] = 1;
            }
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 2; j <= m; j++) {
                if (map[i][j] != map[i][j - 1]) {
                    l[i][j] = l[i][j - 1];
                }
            }
        }
        for (int i = 1; i <= n; i++) {
            for (int j = m - 1; j >= 1; j--) {
                if (map[i][j + 1] != map[i][j]) {
                    r[i][j] = r[i][j + 1];
                }
            }
        }
        int square = 1;
        int seq = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (i != 1 && map[i][j] != map[i - 1][j]) {
                    up[i][j] = up[i - 1][j] + 1;
                    l[i][j] = Math.max(l[i][j], l[i - 1][j]);
                    r[i][j] = Math.min(r[i][j], r[i - 1][j]);
                }
                seq = Math.max(seq, up[i][j] * (r[i][j] - l[i][j] + 1));
                square = Math.max(square, Math.min(up[i][j], r[i][j] - l[i][j] + 1));
            }
        }
        System.out.println(square * square);
        System.out.println(seq);
    }
}
