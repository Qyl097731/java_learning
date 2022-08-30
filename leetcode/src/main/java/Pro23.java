import common.ListNode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * projectName:  fushi
 * packageName: leetcode
 * date: 2022-03-27 22:46
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Pro23 {
}
class Solution23 {
    public ListNode mergeKLists(ListNode[] lists) {
        if(lists.length == 0){
            return null;
        }
        ListNode res = new ListNode();
        PriorityQueue<ListNode>queue = new PriorityQueue<>(lists.length, new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                return o1.val - o2.val;
            }
        });
        ListNode resList = new ListNode(0) , cur = resList;
        queue.addAll(Arrays.stream(lists).filter(Objects::nonNull).collect(Collectors.toList()));
        while(!queue.isEmpty()){
            cur.next = queue.poll();
            cur = cur.next;
            if(cur.next != null) {
                queue.add(cur.next);
            }
        }
        return resList.next;
    }
}
