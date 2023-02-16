package daily;

import common.ListNode;

/**
 * @description
 * @date 2023/2/16 23:36
 * @author: qyl
 */
public class Solution23 {
    public ListNode mergeKLists(ListNode[] lists) {
        return mergeKLists (lists, 0, lists.length - 1);
    }

    private ListNode mergeKLists(ListNode[] lists, int l, int r) {
        if (l == r) {
            return lists[l];
        }
        if (l > r) {
            return null;
        }
        int mid = (l + r) >>> 1;
        return merge (mergeKLists (lists, l, mid), mergeKLists (lists, mid + 1, r));
    }

    private ListNode merge(ListNode a, ListNode b) {
        if (a == null || b == null) {
            return a == null ? b : a;
        }
        ListNode head = new ListNode (-1, null), tail = head;
        while (a != null && b != null) {
            if (a.val <= b.val) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        tail.next = a == null ? b : a;
        return head.next;
    }
}
