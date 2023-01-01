package daily;

import common.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * @description
 * @date:2023/1/1 18:14
 * @author: qyl
 */
public class Offer52 {
    ListNode getIntersectionNode(ListNode headA, ListNode headB) {
//        return solve1(headA,headB);
        return solve2 (headA, headB);
    }

    private ListNode solve2(ListNode headA, ListNode headB) {
        ListNode a = headA, b = headB;
        while (a != b) {
            a = a == null ? headB : a.next;
            b = b == null ? headA : b.next;
        }
        return a;
    }

    private ListNode solve1(ListNode headA, ListNode headB) {
        Set<ListNode> set = new HashSet<> ();
        while (headA != null) {
            set.add (headA);
            headA = headB.next;
        }
        while (headB != null) {
            if (set.contains (headB)) {
                return headB;
            }
            headB = headB.next;
        }
        return null;
    }
}
