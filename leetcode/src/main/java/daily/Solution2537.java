package daily;

import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @date:2023/1/22 18:34
 * @author: qyl
 */
public class Solution2537 {
    public long countGood(int[] nums, int k) {
        long res = 0;
        int l = 0, r = 0, n = nums.length, count = 0;
        Map<Integer, Long> map = new HashMap<> ();
        while (r < n) {
            long cnt = map.getOrDefault (nums[r], 0L);
            map.put (nums[r], cnt + 1);
            count += cnt;
            while (count >= k) {
                res += n - r;
                map.put (nums[l], map.get (nums[l]) - 1);
                count -= map.get (nums[l]);
                l++;
            }
            r++;
        }
        return res;
    }
}
