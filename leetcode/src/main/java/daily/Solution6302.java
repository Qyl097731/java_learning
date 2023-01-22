package daily;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

/**
 * @description
 * @date:2023/1/21 22:05
 * @author: qyl
 */
public class Solution6302 {
    public long maxScore(int[] nums1, int[] nums2, int k) {
        int n = nums1.length;
        Integer[] index = IntStream.range (0, n).boxed ().toArray (Integer[]::new);
        Arrays.sort (index, (a, b) -> nums2[b] - nums2[a]);
        PriorityQueue<Integer> queue = new PriorityQueue<> ();
        long res = 0L, sum = 0;
        for (int i = 0; i < n; i++) {
            sum += nums1[index[i]];
            queue.offer (nums1[index[i]]);
            if (queue.size () > k) {
                sum -= queue.poll ();
            }
            res = Math.max (res, queue.size () < k ? 0 : sum * nums2[index[i]]);
        }
        return res;
    }
}
