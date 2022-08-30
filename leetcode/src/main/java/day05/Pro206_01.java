package day05;

import common.ListNode;

/**
 * @description
 * @date:2022/8/28 15:00
 * @author: qyl
 */
public class Pro206_01 {
    public static void main(String[] args) {
        ListNode head = null;
        int[] vals = new int[]{1, 2, 3, 4, 5};
        Solution206_01 solution206_01 = new Solution206_01();
        for (int i = 0; i < vals.length; i++) {
            head = ListNode.insertNode(head,vals[i]);
        }
        head = solution206_01.reverseList(head);
        ListNode.print(head);
    }

}
class Solution206_01 {
    public ListNode reverseList(ListNode head) {
        ListNode newHead = head;
        if (head != null && head.next != null){
            newHead = reverseList(head.next);
            head.next.next = head;
            head.next = null;
        }
        return newHead;
    }
}
