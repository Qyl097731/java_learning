package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date 2023/3/17 21:54
 * @author: qyl
 */
public class Offer14 {
    static final int MOD = (int) 1e9 + 7;

    long quickPow(long n, long m) {
        long res = 1;
        while (m != 0) {
            if ((m & 1) == 1) {
                res = (res * n) % MOD;
            }
            m >>= 1;
            n = n * n % MOD;
        }
        return res;
    }

    public int cuttingRope(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }
        if (n % 3 == 1) {
            return (int) (quickPow (3, n / 3 - 1) * 4 % MOD);
        } else if (n % 3 == 2) {
            return (int) (quickPow (3, n / 3) * 2 % MOD);
        } else {
            return (int) (quickPow (3, n / 3));
        }
    }

    @Test
    public void test() {
        cuttingRope (3);
    }
}
