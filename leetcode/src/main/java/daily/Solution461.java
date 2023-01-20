package daily;

/**
 * @description
 * @date:2023/1/20 20:26
 * @author: qyl
 */
public class Solution461 {
    public int hammingDistance(int x, int y) {
        return bitCounts (x ^ y);
    }

    private int bitCounts(int n) {
        int res = 0;
        while (n > 0) {
            n &= (n - 1);
            res++;
        }
        return res;
    }
}
