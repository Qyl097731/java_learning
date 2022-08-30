package day06;

import common.ListNode;

/**
 * @author qyl
 * @program Pro25_01.java
 * @Description K 个一组翻转链表
 * @createTime 2022-08-30 10:49
 */
public class Pro25_01 {
    public static void main(String[] args) {
        ListNode head = ListNode.createList(new int[]{1, 2,3,4});

        ListNode.print(new Solution25_01().reverseKGroup(head, 2));
    }
}

class Solution25_01 {
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null) {
            return null;
        }

        int cnt = getLen(head) / k;
        ListNode cur = head, tail = cur;
        ListNode[][] nodes = new ListNode[2][2];
        for (int i = 0; i < cnt; i++) {
            for (int j = 0; j < k; j++) {
                tail = tail.next;
            }
            nodes[i&1] = reverse(cur, k);

            if (i == 0) {
                head = nodes[i&1][0];
            }
            cur = tail;
            if (nodes[(i+1)&1][1] != null){
                nodes[(i+1)&1][1].next = nodes[i&1][0] ;
            }
        }
        if (nodes[(cnt+1)&1][1] != null){
            nodes[(cnt+1)&1][1].next = tail ;
        }
        return head;
    }

    public ListNode[] reverse(ListNode head, int k) {
        ListNode pre = null, cur = head;
        while (cur != null && k > 0) {
            ListNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
            k--;
        }
        return new ListNode[]{pre, head};
    }

    public int getLen(ListNode head) {
        int len = 0;
        while (head != null) {
            len++;
            head = head.next;
        }
        return len;
    }
}