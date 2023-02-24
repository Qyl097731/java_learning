package daily;

import java.util.HashSet;
import java.util.Set;

/**
 * @description
 * @date 2023/2/24 21:56
 * @author: qyl
 */
public class Solution2357 {
    public int minimumOperations(int[] nums) {
        Set<Integer> set = new HashSet<> ();
        for (int num : nums) {
            if (num != 0)
                set.add (num);
        }
        return set.size ();
    }
}
