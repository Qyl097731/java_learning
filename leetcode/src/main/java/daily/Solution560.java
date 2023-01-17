package daily;

import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @date:2023/1/17 21:46
 * @author: qyl
 */
public class Solution560 {
    public int subarraySum(int[] nums, int k) {
        int res = 0;
        Map<Integer, Integer> map = new HashMap<> ();
        map.put (0, 1);
        int sum = 0;
        for (int num : nums) {
            sum += num;
            res += map.getOrDefault (sum - k, 0);
            map.put (sum - k, map.getOrDefault (sum - k, 0) + 1);
        }
        return res;
    }
}
