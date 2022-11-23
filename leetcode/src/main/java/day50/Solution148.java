package day50;

import common.ListNode;

/**
 * @description
 * @date:2022/11/23 0:30
 * @author: qyl
 */
public class Solution148 {
    public ListNode sortList(ListNode head) {
        return sortList (head, null);
    }

    private ListNode sortList(ListNode head, ListNode tail) {
        if (head == null) {
            return head;
        }
        if (head.next == tail) {
            head.next = null;
            return head;
        }
        ListNode slow = head, fast = head;
        while (fast != tail) {
            slow = slow.next;
            fast = fast.next;
            if (fast != tail) {
                fast = fast.next;
            }
        }
        ListNode pre = sortList (head, slow);
        ListNode post = sortList (slow, tail);
        return merge (pre, post);
    }

    private ListNode merge(ListNode pre, ListNode post) {
        ListNode head = new ListNode ( ), node = head;
        while (pre != null && post != null) {
            if (pre.val <= post.val) {
                node.next = pre;
                pre = pre.next;
            } else {
                node.next = post;
                post = post.next;
            }
            node = node.next;
        }
        if (pre != null) {
            node.next = pre;
        } else {
            node.next = post;
        }
        return head.next;
    }
}
