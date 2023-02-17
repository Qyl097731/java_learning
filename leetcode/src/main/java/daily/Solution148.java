package daily;

import common.ListNode;
import org.junit.jupiter.api.Test;

/**
 * @description
 * @date 2023/2/17 13:22
 * @author: qyl
 */
public class Solution148 {
    @Test
    public void test() {
        ListNode head = ListNode.createList (new int[]{4, 1, 2, 3});
        sortList (head);
    }

    public ListNode sortList(ListNode head) {
        return sortList (head, null);
    }

    private ListNode sortList(ListNode head, ListNode tail) {
        ListNode slow = head, fast = head;
        if (head == null) {
            return null;
        }
        if (head.next == tail) {
            head.next = null;
            return head;
        }
        while (fast != tail) {
            slow = slow.next;
            fast = fast.next;
            if (fast != tail) {
                fast = fast.next;
            }
        }
        ListNode left = sortList (head, slow);
        ListNode right = sortList (slow, fast);
        return merge (left, right);
    }

    private ListNode merge(ListNode left, ListNode right) {
        ListNode head = new ListNode (-1, null), cur = head;
        while (left != null && right != null) {
            if (left.val <= right.val) {
                cur.next = left;
                left = left.next;
            } else {
                cur.next = right;
                right = right.next;
            }
            cur = cur.next;
        }
        cur.next = left == null ? right : left;
        return head.next;
    }
}
