package daily;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @description
 * @date:2022/12/13 14:50
 * @author: qyl
 */
public class Solution207 {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] in = new int[numCourses];
        Queue<Integer> queue = new ArrayDeque<> ( );
        List<Integer>[] edges = new List[numCourses];
        for (int i = 0; i < numCourses; i++) {
            edges[i] = new ArrayList<> ( );
        }
        for (int[] prerequisite : prerequisites) {
            int u = prerequisite[0], v = prerequisite[1];
            in[v]++;
            edges[u].add (v);
        }
        for (int i = 0; i < numCourses; i++) {
            if (in[i] == 0) {
                queue.add (i);
            }
        }
        int cnt = 0;
        while (!queue.isEmpty ( )) {
            int u = queue.poll ( );
            cnt++;
            for (int i = 0; i < edges[u].size ( ); i++) {
                int v = edges[u].get (i);
                in[v]--;
                if (in[v] == 0) {
                    queue.add (v);
                }
            }
        }
        return cnt == numCourses;
    }
}
