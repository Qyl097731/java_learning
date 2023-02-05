package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date 2023/2/4 23:34
 * @author: qyl
 */
public class Solution6345 {
    public int maximizeWin(int[] prizePositions, int k) {
        int n = prizePositions.length;
        int[] pre = new int[n + 1];
        int l = 0, res = 0;
        for (int r = 0; r < n; r++) {
            while (prizePositions[r] - prizePositions[l] > k) l++;
            res = Math.max (res, r - l + 1 + pre[l]);
            pre[r + 1] = Math.max (pre[r], r - l + 1);
        }
        return res;
    }

    @Test
    public void test() {
        System.out.println (maximizeWin (new int[]{18, 20, 20, 21, 21, 22, 22, 23, 23, 24, 24}, 2));
    }
}
