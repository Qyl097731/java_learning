package daily;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @description
 * @date:2022/12/13 11:19
 * @author: qyl
 */
public class Shunfeng01 {
    public boolean hasCycle(String graph) {
        String[] edges = graph.split (",");
        ArrayList<Integer>[] g = new ArrayList[104];
        for (int i = 0; i < g.length; i++) {
            g[i] = new ArrayList ( );
        }
        int[] in = new int[104];
        Set<Integer> ver = new HashSet<> ( );
        for (String edge : edges) {
            String[] nums = edge.split ("->");
            int u = Integer.parseInt (nums[0]);
            int v = Integer.parseInt (nums[1]);
            g[u].add (v);
            in[v]++;
            ver.add (u);
            ver.add (v);
        }
        Queue<Integer> queue = new ArrayDeque<> ( );
        for (int v : ver) {
            if (in[v] == 0) {
                queue.add (v);
            }
        }

        int cnt = 0;
        while (!queue.isEmpty ( )) {
            int v = queue.poll ( );
            cnt++;
            for (int i = 0; i < g[v].size ( ); i++) {
                int to = g[v].get (i);
                in[to]--;
                if (in[to] == 0) {
                    queue.add (to);
                }
            }
        }
        return cnt != ver.size ( );
    }

    @Test
    public void test() {
        String s = "1->2,2->3,3->1";
        System.out.println (hasCycle (s));
    }
}
