package daily;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @description
 * @date:2023/1/17 22:00
 * @author: qyl
 */
public class Solution739 {
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] res = new int[n];
        Deque<Integer> queue = new ArrayDeque<Integer> ();
        int idx = 0;
        while (idx < n) {
            if (queue.isEmpty ()) {
                queue.offerLast (idx);
            } else {
                while (!queue.isEmpty () && temperatures[idx] > temperatures[queue.peekLast ()]) {
                    int resIndex = queue.pollLast ();
                    res[resIndex] = idx - resIndex;
                }
            }
            queue.offerLast (temperatures[idx]);
            idx++;
        }
        return res;
    }

    @Test
    public void test() {
        dailyTemperatures (new int[]{73, 74, 75, 71, 69, 72, 76, 73});
    }
}
