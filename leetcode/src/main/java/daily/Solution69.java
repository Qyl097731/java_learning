package daily;

/**
 * @description
 * @date 2023/2/16 23:26
 * @author: qyl
 */
public class Solution69 {
    public int mySqrt(long x) {
        long l = 0, r = Integer.MAX_VALUE, mid;
        long res = 0;
        while (l <= r) {
            mid = (l + r) >>> 1;
            if (mid * mid <= x) {
                res = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return (int) res;
    }
}
