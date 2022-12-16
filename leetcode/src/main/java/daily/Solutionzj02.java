package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/12/16 17:04
 * @author: qyl
 */
public class Solutionzj02 {
    int[] pre;
    int res = Integer.MAX_VALUE;

    public int minSwaps(int[] chess) {
        int n = chess.length;
        pre = new int[n + 1];
        int tot = 0;
        for (int i = 1; i <= n; i++) {
            if (chess[i - 1] == 1) {
                tot++;
            }
            pre[i] = i - tot;
        }
        for (int i = 1; i + tot - 1 <= n; i++) {
            res = Math.min (pre[i + tot - 1] - pre[i - 1], res);
        }
        return res;
    }

    @Test
    public void test() {
        int[] a = {1, 0, 0, 1, 1, 1};
        minSwaps (a);
    }

}
