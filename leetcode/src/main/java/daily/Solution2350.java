package daily;

import java.util.HashSet;
import java.util.Set;

/**
 * @description
 * @date:2023/1/5 19:06
 * @author: qyl
 */
public class Solution2350 {
    public int shortestSequence(int[] rolls, int k) {
        int res = 0;
        Set<Integer> set = new HashSet<> ();
        for (int roll : rolls) {
            set.add (roll);
            if (set.size () == k) {
                set.clear ();
                res++;
            }
        }
        return res + 1;
    }
}
