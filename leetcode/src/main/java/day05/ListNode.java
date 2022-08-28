package day05;

/**
 * projectName:  fushi
 * packageName: leetcode
 * date: 2022-03-27 22:01
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }

    static ListNode insertNode(ListNode head,int val) {
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

    static void print(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
    }
}
