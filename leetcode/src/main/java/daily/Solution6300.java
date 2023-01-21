package daily;

import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @date:2023/1/21 22:05
 * @author: qyl
 */
public class Solution6300 {
    public int getCommon(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<> ();
        for (int num : nums1) {
            map.put (num, 1);
        }
        for (int num : nums2) {
            if (map.containsKey (num)) {
                return num;
            }
        }
        return -1;
    }
}
