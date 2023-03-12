package daily;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @description
 * @date 2023/3/12 21:59
 * @author: qyl
 */
public class Offer59 {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        Deque<Integer> q = new ArrayDeque<> ();
        for (int i = 0; i < k - 1; i++) {
            while (!q.isEmpty () && nums[i] >= nums[q.peekLast ()]) {
                q.pollLast ();
            }
            q.offerLast (i);
        }
        int[] result = new int[n - k + 1];
        int index = 0;
        for (int i = k - 1; i < n; i++) {
            while (!q.isEmpty () && nums[i] >= nums[q.peekLast ()]) {
                q.pollLast ();
            }
            q.offerLast (i);
            while (!q.isEmpty () && i - q.peekFirst () + 1 > k) {
                q.pollFirst ();
            }
            result[index++] = nums[q.peekFirst ()];
        }
        return result;
    }

    @Test
    public void test() {
        maxSlidingWindow (new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3);
    }
}
