package daily;

import common.ListNode;

/**
 * @description
 * @date:2023/1/30 23:22
 * @author: qyl
 */
public class Solution1669 {
    public ListNode mergeInBetween(ListNode l1, int a, int b, ListNode l2) {
        int cur = 0;
        ListNode first = l1, second = first;
        while (cur + 1 != a) {
            first = first.next;
            cur++;
        }
        second = first;
        while (cur - 1 != b) {
            second = second.next;
            cur++;
        }
        first.next = l2;
        while (l2.next != null) {
            l2 = l2.next;
        }
        l2.next = second;
        return first;
    }
}
