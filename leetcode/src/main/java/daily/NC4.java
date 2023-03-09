package daily;

import common.ListNode;

/**
 * @description
 * @date 2023/3/9 21:54
 * @author: qyl
 */
public class NC4 {
    public boolean hasCycle(ListNode head) {
        if (head == null) return false;
        ListNode fast = head.next, slow = head;
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
            if (fast != null) {
                fast = fast.next;
            }
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }
}
