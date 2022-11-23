package day50;


/**
 * @description
 * @date:2022/11/22 23:59
 * @author: qyl
 */
public class Solution878 {
    public int nthMagicalNumber(int n, int a, int b) {
        int c = a * b / gcd (a, b);
        int mod = (int) 1e9 + 7;
        long l = 1, r = Math.min (a, b) * n, mid;
        int ans = -1;
        while (l <= r) {
            mid = (l + r) / 2;
            if (mid / a + mid / b - mid / c >= n) {
                ans = (int) (mid % mod);
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return ans;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd (b, a % b);
    }
}
