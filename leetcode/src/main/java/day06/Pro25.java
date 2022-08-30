package day06;

import common.ListNode;

import java.util.*;

/**
 * @author qyl
 * @program Pro25.java
 * @Description K 个一组翻转链表
 * @createTime 2022-08-30 09:47
 */
public class Pro25 {
    public static void main(String[] args) {
        ListNode head = ListNode.createList(new int[]{1, 2, 3});

        ListNode.print(new Solution25().reverseKGroup(head,2));
    }
}

class Solution25 {
    public ListNode reverseKGroup(ListNode head, int k) {
        List<Integer> list = createList(head);
        int cnt = list.size()/k;
        for (int i = 0; i < cnt; i++) {
            Collections.reverse(list.subList(i*k,(i+1)*k));
        }
        Integer[] val = list.toArray(new Integer[0]);
        return createList(val);
    }


    public ListNode createList(Integer[] vals){
        ListNode head = null;
        for (int i = 0; i < vals.length; i++) {
            head = insertNode(head,vals[i]);
        }
        return head;
    }

    public List<Integer> createList(ListNode head){
        ArrayList<Integer> list = new ArrayList<>();
        while (head != null){
            list.add(head.val);
            head = head.next;
        }
        return list;
    }

    public ListNode insertNode(ListNode head,int val) {
        ListNode node = new ListNode(val, null);
        if (head == null) {
            return head = node;

        }
        ListNode temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = node;
        return head;
    }

}