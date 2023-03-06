package day06;

import common.ListNode;

/**
 * @author qyl
 * @program Pro25.java
 * @Description K 个一组翻转链表
 * @createTime 2022-08-30 09:47
 */
public class Pro25 {
    public static void main(String[] args) {
        ListNode head = ListNode.createList (new int[]{1, 2, 3, 4, 5});

        ListNode.print(new Solution25().reverseKGroup(head,2));
    }
}

class Solution25 {
    public ListNode reverseKGroup(ListNode head, int k) {
        return reverseGroup (head, k)[0];
    }

    ListNode[] reverseGroup(ListNode head, int k) {
        int count = 1;
        ListNode tail = head;
        while (tail != null && count < k) {
            tail = tail.next;
            count++;
        }
        if (tail == null) {
            return new ListNode[]{head, null};
        }
        ListNode[] groups = reverseGroup (tail.next, k);

        ListNode[] nodes = reverse (head, tail);
        nodes[1].next = groups[0];
        return new ListNode[]{nodes[0], groups[1]};
    }

    ListNode[] reverse(ListNode head, ListNode tail) {
        ListNode prev = head, cur = head.next;
        while (prev != tail) {
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return new ListNode[]{tail, head};
    }

}
