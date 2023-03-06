package daily;

import common.ListNode;

/**
 * @description
 * @date 2023/3/6 19:50
 * @author: qyl
 */
public class NC53 {
    static int cur = 0;

    public ListNode removeNthFromEnd(ListNode head, int n) {
        return remove (head, n);
    }

    private ListNode remove(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        head.next = remove (head.next, n);
        cur++;
        if (cur == n) {
            return head.next;
        } else {
            return head;
        }
    }
}
