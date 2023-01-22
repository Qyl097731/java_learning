package daily;

/**
 * @description
 * @date:2023/1/22 16:46
 * @author: qyl
 */
public class Solution2543 {
    public boolean isReachable(int targetX, int targetY) {
        while (targetX % 2 == 0) {
            targetX >>= 1;
        }
        while (targetY % 2 == 0) {
            targetY >>= 1;
        }
        return gcd (targetX, targetY) == 1;
    }

    int gcd(int x, int y) {
        return y == 0 ? x : gcd (y, x % y);
    }
}
