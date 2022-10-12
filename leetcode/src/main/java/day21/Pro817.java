package day21;

import common.ListNode;

import java.util.*;

/**
 * @description
 * @date:2022/10/12 0:10
 * @author: qyl
 */

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution817 {
    public int numComponents(ListNode head, int[] nums) {
        int len = nums.length, res = 0;
        int[] set = new int[100000];
        for (int i = 0; i < len; i++) {
            set[nums[i]] = 1;
        }

        ListNode tail = head;
        while(head != null){
            int num = head.val;
            if (set[num] == 1) {
                tail = head;
                while (tail != null && set[tail.val] == 1) {
                    tail = tail.next;
                }
                head = tail;
                res++;
            }else{
                head = head.next;
            }
        }
        return res;
    }
}
