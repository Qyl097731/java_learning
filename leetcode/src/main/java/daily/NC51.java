package daily;

import common.ListNode;

import java.util.ArrayList;

/**
 * @description
 * @date 2023/3/9 18:28
 * @author: qyl
 */
public class NC51 {
    public ListNode mergeKLists(ArrayList<ListNode> lists) {
        int n = lists.size ();
        return mergeKLists (lists, 0, n - 1);
    }

    private ListNode mergeKLists(ArrayList<ListNode> lists, int start, int end) {
        if (start == end) {
            return lists.get (start);
        }
        int mid = (start + end) / 2;
        ListNode left = mergeKLists (lists, start, mid);
        ListNode right = mergeKLists (lists, mid + 1, end);
        return merge (left, right);
    }

    private ListNode merge(ListNode left, ListNode right) {
        ListNode head = new ListNode (0), cur = head;
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        while (left != null && right != null) {
            ListNode node;
            if (left.val <= right.val) {
                node = new ListNode (left.val);
                left = left.next;
            } else {
                node = new ListNode (right.val);
                right = right.next;
            }
            cur.next = node;
            cur = cur.next;
        }
        if (left != null) {
            cur.next = left;
        } else {
            cur.next = right;
        }
        return head.next;
    }
}
