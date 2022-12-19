package daily;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @description
 * @date:2022/12/19 16:01
 * @author: qyl
 */
public class Offer03 {
    public int findRepeatNumber(int[] nums) {
//        return solve1(nums);
        return solve2 (nums);
    }

    private int solve2(int[] nums) {
        Set<Integer> set = new HashSet<> ( );
        for (int num : nums) {
            if (set.contains (num)) return num;
            set.add (num);
        }
        return -1;
    }

    private int solve1(int[] nums) {
        Map<Integer, Integer> cnt = new HashMap<> ( );
        int res = -1;
        for (int num : nums) {
            cnt.put (num, cnt.getOrDefault (num, 0) + 1);
        }
        for (Map.Entry<Integer, Integer> entry : cnt.entrySet ( )) {
            if (entry.getValue ( ) > 1) {
                res = entry.getKey ( );
                break;
            }
        }
        return res;
    }
}
