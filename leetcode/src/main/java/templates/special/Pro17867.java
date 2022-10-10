package templates.special;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @description
 * @date:2022/10/10 11:05
 * @author: qyl
 */
public class Pro17867 {
    static long m, n;
    static long[][][] dp = new long[20][10][2];
    static int[] digit = new int[20];

    public static void main(String[] args) {
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[j].length; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }
        Scanner scanner = new Scanner(System.in);
        m = scanner.nextLong();
        n = scanner.nextLong();
        long cntm = solve(m);
        long l = 1,r = n * 7 + 7 + m , mid;
        while (l < r) {
            mid = (l + r) >> 1;
            long cntmid = solve(mid);
            if (cntmid - cntm >= n) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        System.out.println(r);
    }

    static long solve(long num) {
        int cnt = 0;
        while (num > 0) {
            digit[cnt++] = (int) (num % 10);
            num /= 10;
        }
        return dfs(cnt - 1, 0, 0, true);
    }

    private static long dfs(int pos, int mod, int hasSeven, boolean limit) {
        long sum = 0;
        if (pos == -1) {
            return (hasSeven == 1 || mod % 7 == 0) ? 1L : 0L;
        }
        if (!limit && dp[pos][mod][hasSeven] != -1) {
            return dp[pos][mod][hasSeven];
        }
        int up = limit ? digit[pos] : 9;

        for (int i = 0; i <= up; i++) {
            sum += dfs(pos - 1, (mod * 10 + i) % 7, (i == 7 || hasSeven == 1) ? 1 : 0, limit && i == up);
        }
        if (!limit) {
            dp[pos][mod][hasSeven] = sum;
        }
        return sum;
    }
}
