package daily;

import common.ListNode;

/**
 * @description
 * @date 2023/3/3 20:44
 * @author: qyl
 */
public class Solution206_2 {
    public ListNode reverseList(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode[] nodes = reverse (head);
        nodes[1].next = null;
        return nodes[0].next;
    }

    ListNode[] reverse(ListNode node) {
        if (node.next == null) {
            return new ListNode[]{new ListNode (-1, node), node};
        }
        ListNode[] nodes = reverse (node.next);
        nodes[1].next = node;
        return new ListNode[]{nodes[0], node};
    }
}
