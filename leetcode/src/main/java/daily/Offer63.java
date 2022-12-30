package daily;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @description
 * @date:2022/12/30 18:41
 * @author: qyl
 */
public class Offer63 {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        if (n <= 1) {
            return 0;
        }
        Deque<Integer> queue = new ArrayDeque<> ();
        int res = -1;
        for (int i = 0; i < n; i++) {
            while (!queue.isEmpty () && queue.peekLast () >= prices[i]) {
                res = Math.max (queue.peekLast () - queue.peekFirst (), res);
                queue.pollLast ();
            }
            queue.offerLast (prices[i]);
        }
        res = Math.max (queue.peekLast () - queue.peekFirst (), res);
        return res;
    }

    @Test
    public void test() {
        System.out.println (maxProfit (new int[]{7, 1, 5, 3, 6, 4}));
    }
}
