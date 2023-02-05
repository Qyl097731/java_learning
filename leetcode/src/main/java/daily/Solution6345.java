package daily;

import java.util.*;

/**
 * @description
 * @date 2023/2/5 23:37
 * @author: qyl
 */
public class Solution6345 {
    public long minCost(int[] basket1, int[] basket2) {
        long res = 0;
        Map<Integer, Integer> count = new HashMap<> ();
        for (int b : basket1) {
            count.put (b, count.getOrDefault (b, 0) + 1);
        }
        for (int b : basket2) {
            count.put (b, count.getOrDefault (b, 0) - 1);
        }
        int mn = Integer.MAX_VALUE;
        List<Integer> list1 = new ArrayList<> (), list2 = new ArrayList<> ();
        for (Map.Entry<Integer, Integer> entry : count.entrySet ()) {
            int key = entry.getKey ();
            mn = Math.min (mn, key);
            int val = entry.getValue ();
            if (val % 2 != 0) {
                return -1;
            } else {
                val /= 2;
                while (val < 0) {
                    list1.add (key);
                    val++;
                }
                while (val > 0) {
                    list2.add (key);
                    val--;
                }
            }
        }
        Collections.sort (list1);
        Collections.sort (list2, (a, b) -> Integer.compare (b, a));
        for (int i = 0; i < list1.size (); i++) {
            res += Math.min (Math.min (list1.get (i), list2.get (i)), mn * 2);
        }
        return res;
    }
}
