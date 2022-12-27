package daily;

/**
 * @description
 * @date:2022/12/27 20:00
 * @author: qyl
 */
public class Offer10l {
    final int mod = (int) 1e9 + 7;

    public int fib(int n) {
//        return solve1(n);
        return solve2 (n);
    }

    private int solve2(int n) {
        if (n < 2) {
            return n;
        }

        int[][] base = {{1, 1}, {1, 0}};
        int[][] res = pow (base, n - 1);
        return res[0][0];
    }

    private int[][] pow(int[][] base, int n) {
        int[][] ret = {{1, 0}, {0, 1}};
        while (n > 0) {
            if ((n & 1) == 1) {
                ret = mult (ret, base);
            }
            n >>= 1;
            base = mult (base, base);
        }
        return ret;
    }

    private int[][] mult(int[][] a, int[][] b) {
        int[][] c = new int[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                c[i][j] = (int) (((long) a[i][0] * b[0][j] % mod + (long) a[i][1] * b[1][j] % mod) % mod);
            }
        }
        return c;
    }

    private int solve1(int n) {
        int f1 = 0, f2 = 1;
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int f3 = 0;
        for (int i = 2; i <= n; i++) {
            f3 = (f2 + f1) % mod;
            f1 = f2;
            f2 = f3;
        }
        return f3;
    }
}
