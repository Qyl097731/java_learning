package day26;

import common.ListNode;
import org.junit.jupiter.api.Test;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution61{
    public ListNode rotateRight(ListNode head, int k) {
        int n = 0;
        ListNode cur = head;
        while(cur != null){
            n++;
            cur = cur.next;
        }
        if (n==0){
            return head;
        }
        k = n - k % n; cur = head;
        k--;
        while(k > 0){
            cur = cur.next;
            k--;
        }
        if(cur.next == null){
            return head;
        }
        ListNode thead = head,tcur = cur;
        head = cur.next;
        while(cur.next != null){
            cur = cur.next;
        }
        cur.next = thead;
        tcur.next = null;
        return head;
    }
}
