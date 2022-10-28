package day35;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @description
 * @date:2022/10/28 10:13
 * @author: qyl
 */
public class Solution907 {
    private final int mod = (int) 1e9 + 7;

    public int sumSubarrayMins(int[] arr) {
        Deque<Integer> deque = new ArrayDeque<>();
        int n = arr.length;
        int[] right = new int[n];
        long res = 0;
        for (int i = n - 1; i >= 0; i--) {
            while (!deque.isEmpty() && arr[deque.peekLast()] >= arr[i]) {
                deque.pollLast();
            }
            right[i] = deque.isEmpty() ? n : deque.peekLast();
            deque.offerLast(i);
        }
        deque.clear();
        for (int i = 0; i < n; i++) {
            while (!deque.isEmpty() && arr[deque.peekLast()] > arr[i]) {
                deque.pollLast();
            }
            long left = deque.isEmpty() ? -1 : deque.peekLast();
            res = (res + arr[i] * (right[i]-i)*(i - left))%mod;
            deque.offerLast(i);
        }
        return (int)res;
    }
}
