package daily;

import common.ListNode;
import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/12/18 16:54
 * @author: qyl
 */
public class Offer24 {
    ListNode res = new ListNode (-1, null);

    public ListNode reverseList(ListNode head) {
        ListNode cur = res;
        while (head != null) {
            cur.next = new ListNode (head.val, cur.next);
            head = head.next;
        }
        return res.next;
    }

    @Test
    public void test() {
        ListNode head = ListNode.createList (new int[]{1, 2, 3, 4, 5});
        reverseList (head);
    }
}
