package day51;

import common.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * @description
 * @date:2022/11/23 11:25
 * @author: qyl
 */
public class Solution160 {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
//        return solveA (headA, headB);
        return sloveB (headA, headB);
    }

    private ListNode sloveB(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode p1 = headA, p2 = headB;
        while (p1 != p2) {
            p1 = p1 == null ? headB : p1.next;
            p2 = p2 == null ? headA : p2.next;
        }
        return p1;
    }

    private ListNode solveA(ListNode headA, ListNode headB) {
        Set<ListNode> set = new HashSet<> ( );
        ListNode headC = headA;
        while (headC != null) {
            set.add (headC);
            headC = headC.next;
        }
        headC = headB;
        while (headC != null) {
            if (set.contains (headC)) {
                break;
            }
        }
        return headC;
    }
}
