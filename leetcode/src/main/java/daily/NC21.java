package daily;

import common.ListNode;
import org.junit.jupiter.api.Test;

/**
 * @description
 * @date 2023/3/9 0:34
 * @author: qyl
 */
public class NC21 {
    @Test
    public void test() {
        reverseBetween (ListNode.createList (new int[]{1, 2, 3, 4, 5}), 2, 4);
    }

    public ListNode reverseBetween(ListNode head, int m, int n) {
        ListNode result = new ListNode (0), pre = result, tail, next;
        result.next = head;
        head = result.next;
        int start = 1, end = m;
        while (start < m) {
            pre = head;
            head = head.next;
            start++;
        }
        next = head;
        tail = head;
        while (end <= n) {
            tail = next;
            next = next.next;
            end++;
        }
        ListNode[] node = reverse (head, tail);
        pre.next = node[0];
        node[1].next = next;
        return result.next;
    }

    void print(ListNode head) {
        while (head != null) {
            System.out.println (head.val);
            head = head.next;
        }
    }

    ListNode[] reverse(ListNode head, ListNode tail) {
        if (head == tail) {
            return new ListNode[]{tail, tail};
        }
        ListNode cur = head.next, pre = head, next;
        while (pre != tail) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return new ListNode[]{pre, head};
    }
}
