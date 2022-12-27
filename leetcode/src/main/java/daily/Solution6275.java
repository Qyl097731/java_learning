package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/12/24 22:25
 * @author: qyl
 */
public class Solution6275 {
    public int minimizeSet(int divisor1, int divisor2, int uniqueCnt1, int uniqueCnt2) {
        long l = 1, r = 2000000000, mid, res = 1;
        long mult = ((long) divisor1 / gcd (divisor1, divisor2) * divisor2);
        while (l <= r) {
            mid = (l + r) / 2;
            long cnt1 = Math.max (0, uniqueCnt1 - (mid / divisor2 - mid / mult));
            long cnt2 = Math.max (0, uniqueCnt2 - (mid / divisor1 - mid / mult));
            long common = mid - mid / divisor1 - mid / divisor2 + mid / mult;
            if (common >= cnt1 + cnt2) {
                res = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return (int) res;
    }

    int gcd(int a, int b) {
        return b == 0 ? a : gcd (b, a % b);
    }

    @Test
    public void test() {
        System.out.println (minimizeSet (5, 5, 9, 3));
    }
}
