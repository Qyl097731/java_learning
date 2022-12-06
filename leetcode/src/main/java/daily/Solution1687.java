package daily;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;

/**
 * @description
 * @date:2022/12/6 12:01
 * @author: qyl
 */
public class Solution1687 {
    int n;

    public int boxDelivering(int[][] boxes, int portsCount, int maxBoxes, int maxWeight) {
        n = boxes.length;
        ArrayDeque<Integer> opt = new ArrayDeque<> ( );
        int[] preWeight = new int[n + 5];
        int[] prePort = new int[n + 5];
        calW (preWeight, boxes);
        calP (prePort, boxes);
        opt.offerLast (0);
        int[] f = new int[n + 1];
        int[] g = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            while (i - opt.peekFirst ( ) > maxBoxes || preWeight[i] - preWeight[opt.peekFirst ( )] > maxWeight) {
                opt.pollFirst ( );
            }
            f[i] = g[opt.peekFirst ( )] + prePort[i] + 2;
            if (i != n) {
                g[i] = f[i] - prePort[i + 1];
                while (!opt.isEmpty ( ) && g[i] <= g[opt.peekLast ( )]) {
                    opt.pollLast ( );
                }
                opt.offerLast (i);
            }
        }
        return f[n];
    }

    private void calP(int[] prePort, int[][] boxes) {
        int pre = boxes[0][0];
        for (int i = 2; i <= n; i++) {
            prePort[i] = prePort[i - 1] + (pre != boxes[i - 1][0] ? 1 : 0);
            pre = boxes[i - 1][0];
        }
    }

    private void calW(int[] preWeight, int[][] boxes) {
        for (int i = 1; i <= n; i++) {
            preWeight[i] = preWeight[i - 1] + boxes[i - 1][1];
        }
    }

    @Test
    public void test() {
        int[][] boxes = {{1, 1}, {2, 1}, {1, 1}};
        boxDelivering (boxes, 2, 3, 3);
    }
}
