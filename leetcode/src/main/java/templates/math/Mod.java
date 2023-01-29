package templates.math;

/**
 * @description 取模相加减
 * @date:2022/12/28 0:15
 * @author: qyl
 */
public class Mod {
    static final int mod = (int) 1e9 + 7;

    int add(int x, int y) {
        if ((x += y) >= mod) x -= mod;
        return x;
    }

    int sub(int x, int y) {
        if ((x -= y) < 0) x += mod;
        return x;
    }

    long pow(long x, long y) {
        long res = 1;
        while (y > 0) {
            if ((y & 1) == 1) {
                res = res * x % mod;
            }
            x = x * x % mod;
            y >>= 1;
        }
        return res;
    }
}
