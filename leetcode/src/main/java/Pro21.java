
/**
 * projectName:  fushi
 * packageName: leetcode
 * date: 2022-03-27 21:59
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Pro21 {
    public static void main(String[] args) {
        new Solution21().mergeTwoLists(null,null);
    }
}


// Definition for singly-linked list.


class Solution21 {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if(list1 == null && list2 == null){
            return null;
        }
        ListNode resList = new ListNode(0,new ListNode()), cur = resList;

        while(list1 != null && list2 != null){
            if(list1.val < list2.val){
                cur.next = list1;
                list1 = list1.next;
            }else {
                cur.next = list2;
                list2 = list2.next;
            }
            cur = cur.next;
        }
        while(list2 != null){
            cur.next = list2;
            cur = cur.next;
            list2 = list2.next;
        }
        while(list1 != null){
            cur.next = list1;
            cur = cur.next;
            list1 = list1.next;
        }
        return resList.next;
    }
}
