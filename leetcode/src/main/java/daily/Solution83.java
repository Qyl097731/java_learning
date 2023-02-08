package daily;

import common.ListNode;

/**
 * @description
 * @date 2023/2/8 16:09
 * @author: qyl
 */
public class Solution83 {
    public ListNode deleteDuplicates(ListNode head) {
        ListNode res = new ListNode (-101, head), slow = res, fast = res.next;
        while (fast != null) {
            int x = slow.val;
            if (fast.val == x) {
                while (fast != null && fast.val == x) {
                    fast = fast.next;
                }
                slow.next = fast;
                slow = slow.next;
            } else {
                slow.next = fast;
                fast = fast.next;
                slow = slow.next;
            }
        }
        return res.next;
    }
}
