package daily;

import common.ListNode;
import org.junit.jupiter.api.Test;

/**
 * @description
 * @date 2023/2/8 0:21
 * @author: qyl
 */
public class Solution82 {
    public ListNode deleteDuplicates(ListNode head) {
        ListNode res = new ListNode (-101, head);
        ListNode cur = res;
        while (cur.next != null && cur.next.next != null) {
            if (cur.next.val == cur.next.next.val) {
                int x = cur.next.val;
                while (cur.next != null && cur.next.val == x) {
                    cur.next = cur.next.next;
                }
            } else {
                cur = cur.next;
            }
        }
        return res.next;
    }

    @Test
    public void test() {
        deleteDuplicates (ListNode.createList (new int[]{1, 1}));
    }
}
