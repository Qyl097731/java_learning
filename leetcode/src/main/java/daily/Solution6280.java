package daily;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @description
 * @date:2023/1/1 17:26
 * @author: qyl
 */
public class Solution6280 {
    static int MAXN = 1000005;
    static boolean[] isprime = new boolean[MAXN];
    static int[] prime = new int[MAXN];
    static int cnt = 0;

    static {
        Arrays.fill (isprime, true);
        isprime[1] = false;
        for (int i = 2; i < MAXN; ++i) {
            if (isprime[i]) {
                prime[++cnt] = i;
            }

            for (int j = 1; j <= cnt && i * prime[j] < MAXN; ++j) {
                isprime[i * prime[j]] = false;
                if (i % prime[j] == 0) {
                    break;
                }
            }
        }
    }

    public int[] closestPrimes(int left, int right) {
        if (left == 1 && right == 1) {
            return new int[]{-1, -1};
        }
        int[] res = new int[]{-1, -1, Integer.MAX_VALUE};
        int i;
        for (i = 1; i <= cnt && i < MAXN; i++) {
            if (prime[i] <= right && prime[i] >= left) {
                break;
            }
        }
        for (i++; i < MAXN && prime[i] <= right; i++) {
            if (prime[i] - prime[i - 1] < res[2]) {
                res = new int[]{prime[i - 1], prime[i], prime[i] - prime[i - 1]};
            }
        }
        if (res[0] == -1) {
            return new int[]{-1, -1};
        }
        return new int[]{res[0], res[1]};
    }

    @Test
    public void test() {
        closestPrimes (10, 19);
    }
}
