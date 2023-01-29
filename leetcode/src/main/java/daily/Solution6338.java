package daily;

/**
 * @description
 * @date:2023/1/29 21:55
 * @author: qyl
 */
public class Solution6338 {
    static final int mod = (int) 1e9 + 7;

    public int monkeyMove(int n) {
        return (int) (((pow (2, n) - 2) + mod) % mod);
    }

    long pow(long x, long y) {
        y %= mod;
        long res = 1;
        while (y > 0) {
            if ((y & 1) == 1) {
                res = ((res % mod) * (x % mod) + mod) % mod;
            }
            x = ((x % mod) * (x % mod) + mod) % mod;
            y >>= 1;
        }
        return res;
    }
}
