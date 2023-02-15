package daily;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @description
 * @date 2023/2/15 16:52
 * @author: qyl
 */
public class Solution316 {
    public String removeDuplicateLetters(String s) {
        Deque<Character> stack = new ArrayDeque<> ();
        int n = s.length ();
        int[] left = new int[26];
        boolean[] vis = new boolean[26];
        for (int i = 0; i < n; i++) {
            left[s.charAt (i) - 'a']++;
        }
        for (int i = 0; i < n; i++) {
            char c = s.charAt (i);
            if (!vis[c - 'a']) {
                while (!stack.isEmpty () && stack.peekLast () > c && left[stack.peekLast () - 'a'] > 0) {
                    vis[stack.peekLast () - 'a'] = false;
                    stack.pollLast ();
                }
                stack.offerLast (c);
                vis[c - 'a'] = true;

            }
            left[c - 'a']--;
        }

        StringBuilder sb = new StringBuilder ();
        while (!stack.isEmpty ()) {
            sb.append (stack.pollFirst ());
        }
        return sb.toString ();
    }

    @Test
    public void test() {
        removeDuplicateLetters ("ecbacba");
    }
}
