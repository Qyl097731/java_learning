package daily;

/**
 * @description
 * @date:2023/1/5 18:36
 * @author: qyl
 */
public class Offer16 {
    public double myPow(double x, int n) {
        return n >= 0 ? quickPow (x, n) : 1.0 / quickPow (x, -1L * n);
    }

    private double quickPow(double x, long y) {
        double res = 1.0;
        while (y != 0) {
            if ((y & 1) == 1) {
                res *= x;
            }
            x *= x;
            y >>= 1;
        }
        return res;
    }
}
