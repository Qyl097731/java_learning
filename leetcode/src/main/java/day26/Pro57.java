package day26;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @description
 * @date:2022/10/18 11:37
 * @author: qyl
 */
class Solution57 {
    @Test
    public void test(){
        System.out.println(Arrays.deepToString(insert(new int[][]{{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}}, new int[]{4, 8})));
    }

    public int[][] insert(int[][] intervals, int[] newInterval) {
        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> {
            if (o1[0] == o2[0]){
                return Integer.compare(o1[1],o2[1]);
            }
            return Integer.compare(o1[0],o2[0]);
        });

        queue.addAll(Arrays.asList(intervals));
        queue.add(newInterval);
        List<int[]> res = new ArrayList<>();
        while(!queue.isEmpty()){
            int[] pre = queue.poll();
            if (queue.isEmpty()){
                res.add(pre);
                break;
            }
            int[] cur = queue.poll();

            if (pre[1] < cur[0]){
                res.add(pre);
                queue.add(cur);
            }else {
                if (pre[1] >= cur[1]) {
                    queue.add(pre);
                } else {
                    cur[0] = pre[0];
                    queue.add(cur);
                }
            }
        }
        return res.toArray(new int[0][0]);
    }
}
