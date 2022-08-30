package day05;

import common.ListNode;

/**
 * @description
 * @date:2022/8/28 14:01
 * @author: qyl
 */
public class Pro206 {
    public static void main(String[] args) {
        ListNode head = null;
        int[] vals = new int[]{1, 2, 3, 4, 5};
        Solution206 solution206 = new Solution206();
        for (int i = 0; i < vals.length; i++) {
            head = ListNode.insertNode(head,vals[i]);
        }
        head = solution206.reverseList(head);
        ListNode.print(head);
    }

}

class Solution206 {
    public ListNode reverseList(ListNode head) {
        ListNode cur = head, prev = null;
        while (cur != null) {
            ListNode temp = cur.next;
            cur.next = prev;
            prev = cur;
            cur = temp;
        }
        return prev;
    }
}
