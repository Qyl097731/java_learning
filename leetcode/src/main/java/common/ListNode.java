package common;

/**
 * projectName:  fushi
 * packageName: leetcode
 * date: 2022-03-27 22:01
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class ListNode {
    public int val;
    public ListNode next;
    public ListNode() {}
    public ListNode(int val) { this.val = val; }
    public ListNode(int val, ListNode next) { this.val = val; this.next = next; }

    public static ListNode createList(int[] vals){
        ListNode head = null;
        for (int i = 0; i < vals.length; i++) {
            head = insertNode(head,vals[i]);
        }
        return head;
    }

    public static ListNode insertNode(ListNode head,int val) {
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

    public int getLen(ListNode head){
        int len = 0 ;
        while(head != null){
            len++;head = head.next;
        }
        return len;
    }

    public static void print(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
    }
}
