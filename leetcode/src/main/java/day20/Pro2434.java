package day20;

import java.util.*;

/**
 * @description
 * @date:2022/10/11 18:40
 * @author: qyl
 */
public class Pro2434 {
    public static void main(String[] args) {
        System.out.println(new Solution2434().robotWithString("bdda"));
    }
}

class Solution2434 {
    public String robotWithString(String s) {
        StringBuilder sb = new StringBuilder();
        ArrayDeque<Character> deque = new ArrayDeque<>();
        int len = s.length();
        int[] f = new int[len + 1];
        f[len] = 100;
        for (int i = len - 1; i >= 0; i--) {
            f[i] = Math.min(s.charAt(i) - 'a', f[i + 1]);
        }
        for (int i = 0; i < len; ) {
            char c = s.charAt(i);
            deque.addLast(c);
            i++;
            if (f[i] >= deque.getLast() - 'a') {
                while (!deque.isEmpty() &&  f[i] >= deque.getLast() - 'a') {
                    sb.append(deque.pollLast());
                }
            }
        }
        while (!deque.isEmpty()) {
            sb.append(deque.pollLast());
        }
        return sb.toString();
    }
}
