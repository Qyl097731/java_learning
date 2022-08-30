import common.ListNode;

/**
 * projectName:  fushi
 * packageName: leetcode
 * date: 2022-03-27 21:05
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Pro19 {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode listNode = new Solution19().removeNthFromEnd(head, 1);
        while(listNode!=null){
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }
}

class Solution19 {
    public ListNode removeNthFromEnd(ListNode head, int n) {
//        ListNode res = new ListNode(0,head);
//        ListNode cur = res;
//        int len = getLen(head);
//        for(int i = 1; i < len - n + 1; i++){
//            cur = cur.next;
//        }
//        cur.next = cur.next.next;
//        return res.next;
        ListNode cur = head;
        int len = getLen(head);
        if(len == n){
            head = head.next;
            return head;
        }

        for(int i = 1; i < len - n ; i++){
            cur = cur.next;
        }
        cur.next = cur.next.next;
        return head;
    }
    int getLen(ListNode head){
        int len = 0 ;
        while(head != null){
            len++;
            head = head.next;
        }
        return len;
    }
}
