package day02;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author qyl
 * @program Pro239_1.java
 * @Description 优先队列
 * @createTime 2022-08-25 15:28
 */
public class Pro239_1 {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Solution239_1().maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3)));
    }

}
class Solution239_1 {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int len = nums.length;
        int[] res = new int[len - k + 1];
        PriorityQueue<int[]> pq = new PriorityQueue<>(k + 1, (int[] a, int[] b) -> {
            if (a[0] == b[0]) {
                return -Integer.compare(a[1], b[1]);
            }
            return -Integer.compare(a[0], b[0]);
        });

        for (int i = 0; i < k; i++) {
            pq.offer(new int[]{nums[i], i});
        }
        res[0] = pq.peek()[0];
        for (int i = k; i < len; i++) {
            pq.offer(new int[]{nums[i], i});
            while (pq.peek()[1] < i - k + 1) {
                pq.poll();
            }
            res[i - k + 1] = pq.peek()[0];
        }
        return res;
    }
}