package daily;

import java.util.PriorityQueue;

/**
 * @description
 * @date 2023/2/5 23:03
 * @author: qyl
 */
public class Solution6348 {
    public long pickGifts(int[] gifts, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<> ((o1, o2) -> -Integer.compare (o1, o2));
        long res = 0;
        for (int gift : gifts) {
            queue.offer (gift);
        }
        while (k-- > 0) {
            int num = (int) Math.sqrt (queue.poll ());
            queue.offer (num);
        }
        System.out.println (queue);
        while (!queue.isEmpty ()) {
            res += queue.poll ();
        }
        return res;
    }
}
