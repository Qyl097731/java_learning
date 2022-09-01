package day08;

import common.ListNode;

/**
 * @author qyl
 * @program Pro24.java
 * @Description 两两交换链表中的节点
 * @createTime 2022-09-01 15:08
 */
public class Pro24 {
    public static void main(String[] args) {
        ListNode head = ListNode.createList(new int[]{1});
        ListNode.print(new Solution24().swapPairs(head));
    }
}

class Solution24 {
    public ListNode swapPairs(ListNode head) {
        ListNode cur, next;
        ListNode[][] temp = new ListNode[2][2];
        if (head == null) {
            return null;
        }
        cur = head;
        int len = 0, cnt;
        while (cur != null) {
            cur = cur.next;
            len++;
        }
        cnt = len / 2;
        cur = head;
        next = cur;
        for (int i = 0; i < cnt; i++) {
            for (int j = 0; j < 2; j++) {
                next = next.next;
            }
            temp[i & 1] = reverse(cur);
            if (temp[(i + 1) & 1][0] == null) {
                head = temp[i & 1][0];
            } else {
                temp[(i + 1) & 1][1].next = temp[i & 1][0];
            }
            cur = next;
        }
        if (temp[(cnt + 1) & 1][1] != null) {
            temp[(cnt + 1) & 1][1].next = next;
        }
        return head;
    }

    public ListNode[] reverse(ListNode head) {
        int cnt = 0;
        ListNode cur = head, next, pre = null;
        while (cur != null && cnt < 2) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
            cnt++;
        }
        return new ListNode[]{pre, head};
    }

}