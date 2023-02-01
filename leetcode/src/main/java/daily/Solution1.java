package daily;

import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @date:2023/2/1 0:21
 * @author: qyl
 */
public class Solution1 {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<> ();
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (map.containsKey (target - nums[i])) {
                return new int[]{i, map.get (target - nums[i])};
            }
            map.put (nums[i], i);
        }
        return new int[0];
    }
}
