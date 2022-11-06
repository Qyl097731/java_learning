package day40;

import java.util.*;

/**
 * @description
 * @date:2022/11/6 9:24
 * @author: qyl
 */
public class Solution6301 {

    public long totalCost(int[] costs, int k, int candidates) {

        int n = costs.length;
        long res = 0;

        PriorityQueue<int[]> q = new PriorityQueue<>((o1, o2) -> {
            if (o1[0] == o2[0]){
                if (o1[1] == o2[1]){
                    return o1[2] - o2[2];
                }
                return o1[1] - o2[1];
            }
            return o1[0] - o2[0];
        });

        int l = 0, r = n - 1;
        for (int i = 0; i < candidates; i++) {
            if (l <= r) {
                q.add(new int[]{costs[l], l, 0});
                l++;
            }
        }
        for (int i = 0; i < candidates; i++) {
            if (l <= r) {
                q.add(new int[]{costs[r], r, 1});
                r--;
            }
        }

        for (int i = 0; i < k; i++) {
            int[] worker = q.poll();
            if (worker == null){
                break;
            }
            res += worker[0];
            if (worker[2] ==0 ){
                if (l <= r) {
                    q.add(new int[]{costs[l],l,0});
                    l++;
                }
            }else {
                if (l <= r) {
                    q.add(new int[]{costs[r],r,1});
                    r--;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        new Solution6301().totalCost(new int[]{31,25,72,79,74,65,84,91,18,59,27,9,81,33,17,58}
        ,11
        ,2);
    }
}
