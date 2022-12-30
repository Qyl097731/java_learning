package daily;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description
 * @date:2022/12/30 17:50
 * @author: qyl
 */
public class Solution2032 {
    public List<Integer> twoOutOfThree(int[] nums1, int[] nums2, int[] nums3) {
        Map<Integer, Integer> map = new HashMap<> ();

        for (int num : nums1) {
            map.put (num, map.getOrDefault (num, 0) | 1);
        }

        for (int num : nums2) {
            map.put (num, map.getOrDefault (num, 0) | (1 << 1));
        }

        for (int num : nums3) {
            map.put (num, map.getOrDefault (num, 0) | (1 << 2));
        }
        List<Integer> res = new ArrayList<> ();
        for (Map.Entry<Integer, Integer> entry : map.entrySet ()) {
            int num = entry.getKey ();
            int value = entry.getValue ();
            if (value == 3 || value == 5 || value == 6 || value == 7) {
                res.add (num);
            }
        }
        return res;
    }
}
