package daily;


import java.util.TreeSet;

/**
 * @description
 * @date 2023/3/16 23:55
 * @author: qyl
 */
public class Offer49 {
    public int nthUglyNumber(int n) {
//        return solve1(n);
        return solve2 (n);
    }

    private int solve2(int n) {
        int a = 0, b = 0, c = 0;
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            int min = Math.min (dp[a] * 2, Math.min (dp[b] * 3, dp[c] * 5));
            if (dp[a] * 2 == min) {
                a++;
            }
            if (dp[b] * 3 == min) {
                b++;
            }
            if (dp[c] * 5 == min) {
                c++;
            }
            dp[i] = min;
        }
        return dp[n];
    }

    private int solve1(int n) {
        if (n == 1) {
            return 1;
        }
        TreeSet<Long> set = new TreeSet<> ();

        set.add (1L);
        long result = -1;
        for (int i = 0; i < n; i++) {
            result = set.pollFirst ();
            set.add (result * 2);
            set.add (result * 3);
            set.add (result * 5);
        }
        return (int) result;
    }
}
