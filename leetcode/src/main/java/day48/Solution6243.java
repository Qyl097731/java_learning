package day48;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * @description
 * @date:2022/11/20 10:28
 * @author: qyl
 */
public class Solution6243 {
    long res = 0;

    public long minimumFuelCost(int[][] roads, int seats) {
        int n = roads.length + 1;
        List<Integer>[] g = new List[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<> ( );
        }
        for (int i = 0; i < n; i++) {
            int u = roads[i][0], v = roads[i][1];
            g[u].add (v);
            g[v].add (u);
        }
        dfs (0, -1, seats, g);

        return res;
    }

    long dfs(int cur, int fa, int seats, List<Integer>[] g) {
        long temp = 1;
        List<Integer> edge = g[cur];
        for (int i = 0; i < edge.size ( ); i++) {
            int to = edge.get (i);
            if (to != fa) {
                temp += dfs (to, cur, seats, g);
            }
        }
        res += (cur != 0 ? 1 : 0) * (temp + seats - 1) / seats;
        return temp;
    }

    @Test
    public void test() {
        int[][] road = {{3, 1}, {3, 2}, {1, 0}, {0, 4}, {0, 5}, {4, 6}};
        System.out.println (minimumFuelCost (road, 2));
    }
}
