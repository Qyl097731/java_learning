package daily;

import common.ListNode;

/**
 * @description
 * @date 2023/2/8 15:42
 * @author: qyl
 */
public class Solution86 {
    public ListNode partition(ListNode head, int x) {
        ListNode pre = new ListNode (x - 1, null), last = new ListNode (x + 1, null), cur =
                head, resP = pre, resL = last;
        while (cur != null) {
            if (cur.val < x) {
                pre.next = new ListNode (cur.val, null);
                pre = pre.next;
            } else {
                last.next = new ListNode (cur.val, null);
                last = last.next;
            }
            cur = cur.next;
        }
        pre.next = resL;
        return resP;
    }
}
