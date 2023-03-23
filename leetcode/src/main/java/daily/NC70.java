package daily;

import common.ListNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @description
 * @date 2023/3/23 0:02
 * @author: qyl
 */
public class NC70 {

    @Test
    public void test() {
        sortInList (ListNode.build (new int[]{-1, 0, -2}, 0, new ListNode ()));
    }

    public ListNode sortInList(ListNode head) {
//        return solve1(head);
        return solve2 (head);

    }

    private ListNode solve1(ListNode head) {
        // write code here
        if (head == null) {
            return null;
        }
        List<ListNode> nodes = new ArrayList<> ();
        ListNode cur = head;
        while (cur != null) {
            nodes.add (cur);
            cur = cur.next;
        }
        nodes.sort (Comparator.comparingInt (r -> r.val));
        ListNode pre = new ListNode (-1);
        head = pre;
        for (int i = 0; i < nodes.size (); i++) {
            nodes.get (i).next = null;
            pre.next = nodes.get (i);
            pre = pre.next;
        }
        return head.next;
    }


    private ListNode solve2(ListNode head) {
        return mergeSort (head);
    }

    private ListNode mergeSort(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode slow = head, fast = head.next.next;
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
            if (fast != null) {
                fast = fast.next;
            }
        }
        ListNode next = slow.next;
        slow.next = null;
        return merge (mergeSort (head), mergeSort (next));
    }

    private ListNode merge(ListNode pre, ListNode post) {
        ListNode result = new ListNode (-1), cur = result;
        while (pre != null && post != null) {
            if (pre.val <= post.val) {
                cur.next = pre;
                pre = pre.next;
            } else {
                cur.next = post;
                post = post.next;
            }
            cur = cur.next;
        }
        cur.next = pre != null ? pre : post;
        return result.next;
    }
}
