package day20;

import java.util.*;

/**
 * @description
 * @date:2022/10/11 18:15
 * @author: qyl
 */
public class Pro2432 {
    public static void main(String[] args) {
        System.out.println(new Solution2432().hardestWorker(10,new int[][]{{0, 3}, {2, 5}, {0, 9}, {1, 15}}));
    }
}

class Solution2432 {
    public int hardestWorker(int n, int[][] logs) {
        List<int[]> time =  new ArrayList<>();
        time.add(new int[]{logs[0][0],logs[0][1]});
        for (int i = 1; i < logs.length; i++) {
            time.add(new int[]{logs[i][0],logs[i][1]-logs[i-1][1]});
        }

        time.sort((o1, o2) -> {
            if (o1[1] == o2[1]) {
                return Integer.compare(o1[0], o2[0]);
            }
            return -Integer.compare(o1[1], o2[1]);
        });
        return time.get(0)[0];
    }
}
