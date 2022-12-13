package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/12/13 10:58
 * @author: qyl
 */
public class Shunfeng02 {
    public int minRemainingSpace(int[] N, int V) {
        int n = N.length;
        int[] dp = new int[V + 2];
        for (int i = 0; i < n; i++) {
            for (int j = V; j >= N[i]; j--) {
                dp[j] = Math.max (dp[j], dp[j - N[i]] + N[i]);
            }
        }
        return V - dp[V];
    }

    @Test
    public void test() {
        int[] bags = {8, 1, 12, 7, 9, 7};
        minRemainingSpace (bags, 11);
    }
}
