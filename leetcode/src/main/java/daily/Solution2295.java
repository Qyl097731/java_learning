package daily;

import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @date:2022/12/9 22:35
 * @author: qyl
 */
public class Solution2295 {
    Map<Integer, Integer> map = new HashMap<> ( );

    public int[] arrayChange(int[] nums, int[][] operations) {
        int n = operations.length;
        for (int i = n - 1; i >= 0; i--) {
            map.put (operations[i][0], map.getOrDefault (operations[i][1], operations[i][1]));
        }

        for (int i = 0; i < nums.length; i++) {
            nums[i] = map.getOrDefault (nums[i], nums[i]);
        }

        return nums;
    }
}
