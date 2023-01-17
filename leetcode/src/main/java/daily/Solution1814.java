package daily;

import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @date:2023/1/17 19:16
 * @author: qyl
 */
public class Solution1814 {
    final int mod = (int) (1e9 + 7);

    public int countNicePairs(int[] nums) {
        int n = nums.length;
        Map<Integer, Integer> map = new HashMap<> ();
        for (int i = 0; i < n; i++) {
            int dif = nums[i] - rev (nums[i]);
            map.put (dif, map.getOrDefault (dif, 0) + 1);
        }
        long res = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet ()) {
            res += (long) (entry.getValue () - 1) * entry.getValue () / 2;
            res %= mod;
        }
        return (int) res;
    }

    private int rev(int num) {
        int res = 0;
        while (num > 0) {
            res = res * 10 + num % 10;
            num /= 10;
        }
        return res;
    }
}
