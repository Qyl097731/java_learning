package daily;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @description
 * @date:2022/12/10 22:44
 * @author: qyl
 */
public class Solution6262 {
    Map<Integer, ArrayList<Integer>> out = new HashMap<> ( );

    public int maxStarSum(int[] vals, int[][] edges, int k) {
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            ArrayList<Integer> list1 = out.getOrDefault (u, new ArrayList<> ( ));
            list1.add (v);
            out.put (u, list1);

            ArrayList<Integer> list2 = out.getOrDefault (v, new ArrayList<> ( ));
            list2.add (u);
            out.put (v, list2);
        }

        int res = Arrays.stream (vals).max ( ).getAsInt ( );
        for (Map.Entry<Integer, ArrayList<Integer>> entry : out.entrySet ( )) {
            Integer key = entry.getKey ( );
            ArrayList<Integer> value = entry.getValue ( );
            List<Integer> list = new ArrayList<> ( );
            for (Integer id : value) {
                list.add (vals[id]);
            }

            list.sort ((a, b) -> -Integer.compare (a, b));


            int temp = vals[key];
            for (int i = 0; i < value.size ( ) && i < k; i++) {
                if (list.get (i) <= 0) {
                    break;
                } else {
                    temp += list.get (i);
                }
            }
            res = Math.max (res, temp);
        }
        return res;
    }

    @Test
    public void test() {
        int[] vals = new int[]{-20, -10, 1, 2, 3, 4, 10};
        int[][] e = {{0, 1}, {1, 2}, {1, 3}, {3, 4}, {3, 5}, {3, 6}};
        maxStarSum (vals, e, 2);
    }
}
