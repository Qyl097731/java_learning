package day43;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description
 * @date:2022/11/12 22:28
 * @author: qyl
 */
public class Solution6239 {
    int step = 0, maxV = Integer.MIN_VALUE;
    boolean flag = false;
    Map<Integer, List<Integer>> g = new HashMap<>(), gg = new HashMap<>();
    boolean[] vis = new boolean[100005];
    boolean[] used = new boolean[100005];

    public int mostProfitablePath(int[][] edges, int bob, int[] amount) {

        int n = edges.length;
        int[] edge;
        for (int i = 0; i < n; i++) {
            edge = edges[i];
            if (gg.containsKey(edge[1])) {
                gg.get(edge[1]).add(edge[0]);
            } else {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(edge[0]);
                gg.put(edge[1], list);
            }

            if (gg.containsKey(edge[0])) {
                gg.get(edge[0]).add(edge[1]);
            } else {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(edge[1]);
                gg.put(edge[0], list);
            }


        }
        dfs(0, bob, 0);
        dfss(0, amount[0], 0, amount);
        return maxV;
    }

    private void dfss(int cur, int temp, int stp, int[] amount) {

        List<Integer> edges = gg.get(cur);
        if (edges == null || edges.isEmpty()) {
            maxV = Math.max(maxV, temp);
            return;
        }
        used[cur] = true;
        boolean success = false;
        for (int i = 0; i < edges.size(); i++) {
            int to = edges.get(i);
            if (!used[to]) {
                success = true;
                int tempp = temp;
                int stpp = stp + 1;
                if (stpp * 2 == step && vis[to]) {
                    tempp += amount[to] / 2;
                } else if (stpp * 2 < step || !vis[to]) {
                    tempp += amount[to];
                } else if (stpp * 2 > step && vis[to]) {
                    tempp += 0;
                } else {
                    tempp += amount[to];
                }
                dfss(to, tempp, stpp, amount);
            }
        }
        if (!success) {
            maxV = Math.max(maxV, temp);
            return;
        }
    }

    private void dfs(int cur, int bob, int stp) {
        vis[cur] = true;
        if (bob == cur) {
            flag = true;
            step = stp;
            return;
        }
        if (flag) {
            return;
        }
        List<Integer> edges = gg.get(cur);
        for (int i = 0; i < edges.size(); i++) {
            int to = edges.get(i);
            if (!vis[to]) {
                dfs(to, bob, stp + 1);
            }
            if (flag) return;
        }
        vis[cur] = false;
    }

    @Test
    public void test() {
        System.out.println(mostProfitablePath(new int[][]{{0, 2}, {0, 4}, {1, 3}, {1, 2}}, 1, new int[]{3958, -9854, -8334, -9388, 3410
        }));
    }
}
