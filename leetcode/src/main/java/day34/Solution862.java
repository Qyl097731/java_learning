package day34;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @description
 * @date:2022/10/26 9:06
 * @author: qyl
 */
public class Solution862 {
    public int shortestSubarray(int[] nums, int k) {
        int n = nums.length;
        long[] pre = new long[n + 1];
        for (int i = 0; i < n; i++) {
            pre[i + 1] = nums[i] + pre[i];
        }
        int res = n + 1;
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i <= n; i++) {
            long cur = pre[i];
            while (!queue.isEmpty() && cur - pre[queue.peekFirst()] >= k) {
                res = Math.min(res, i - queue.peekFirst());
                queue.pollFirst();
            }
            while (!queue.isEmpty() && cur <= pre[queue.peekLast()]) {
                queue.pollLast();
            }
            queue.addLast(i);
        }
        return res == n + 1 ? -1 : res;
    }
}
