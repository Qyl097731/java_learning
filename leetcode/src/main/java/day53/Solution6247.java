package day53;

import common.ListNode;
import org.junit.jupiter.api.Test;

import static common.ListNode.build;
import static common.ListNode.print;

/**
 * @description
 * @date:2022/11/27 10:29
 * @author: qyl
 */
public class Solution6247 {

    private int max;

    public ListNode removeNodes(ListNode head) {
        if (head == null) {
            return null;
        }

        head.next = removeNodes (head.next);

        max = Math.max (max, head.val);
        if (head.val < max) {
            return head.next;
        } else {
            return head;
        }

    }

    @Test
    public void test() {
        int[] nums = {5, 2, 13, 3, 8};
        ListNode head = new ListNode (-1, null);
        build (nums, 0, head);
        print (head.next);
        removeNodes (head.next);
    }

}
