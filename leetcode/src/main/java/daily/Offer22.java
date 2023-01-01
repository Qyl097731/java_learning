package daily;

import common.ListNode;

import java.util.LinkedList;

/**
 * @description
 * @date:2023/1/1 17:53
 * @author: qyl
 */
public class Offer22 {
    public ListNode getKthFromEnd(ListNode head, int k) {
//        return solve1(head,k);
        return solve2 (head, k);
    }

    private ListNode solve2(ListNode head, int k) {
        ListNode fast = head, slow = head;
        for (int i = 0; i < k; i++) {
            fast = fast.next;
        }
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    private ListNode solve1(ListNode head, int k) {
        LinkedList<ListNode> list = new LinkedList<> ();
        while (head != null) {
            list.add (0, head);
            head = head.next;
        }
        return list.get (k);
    }
}
