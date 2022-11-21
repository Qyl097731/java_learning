package day49;

import common.ListNode;
import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/11/21 15:41
 * @author: qyl
 */
public class Solution142 {
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        ListNode slow = head, fast = head;
        while (true) {
            if (fast == null || fast.next == null) {
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) break;
        }
        fast = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return fast;
    }

    @Test
    public void test() {
        int[] arry = {3, 2, 0, -4};
        for (int i = 0; i < arry.length; i++) {

        }

    }
}
