package daily;

import common.ListNode;

/**
 * @description
 * @date:2022/12/30 22:05
 * @author: qyl
 */
public class Offer25 {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode (-1, null), res = head;
        while (l1 != null && l2 != null) {
            ListNode node = new ListNode ();
            if (l1.val <= l2.val) {
                node.val = l1.val;
                l1 = l1.next;
            } else {
                node.val = l2.val;
                l2 = l2.next;
            }
            node.next = head.next;
            head.next = node;
            head = head.next;
        }
        while (l1 != null) {
            head.next = l1;
            head = head.next;
            l1 = l1.next;
        }
        while (l2 != null) {
            head.next = l2;
            head = head.next;
            l2 = l2.next;
        }
        return res;
    }
}
