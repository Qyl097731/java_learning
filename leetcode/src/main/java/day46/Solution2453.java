package day46;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @date:2022/11/16 19:26
 * @author: qyl
 */
public class Solution2453 {
    Map<Integer, Integer> map = new HashMap<>();

    public int destroyTargets(int[] nums, int space) {
        for (int num : nums) {
            int mod = num % space;
            map.put(mod, map.getOrDefault(mod, 0) + 1);
        }
        int maxV = Integer.MIN_VALUE;
        int minV = Integer.MAX_VALUE;

        for (int num : nums) {
            int v = map.get(num % space);
            if (v > maxV) {
                maxV = v;
                minV = num;
            }
            if (v == maxV) {
                minV = Math.min(minV, num);
            }
        }


//        int maxV = Integer.MIN_VALUE;
//        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
//            if (entry.getValue() > maxV) {
//                maxV = entry.getValue();
//
//            }
//        }

//        Set<Integer> set = new HashSet<>();
//        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
//            if (entry.getValue() == maxV) {
//                set.add(entry.getKey());
//            }
//        }
//
//        int minV = Integer.MAX_VALUE;
//        for (int num : nums) {
//            if (set.contains(num % space)) {
//                minV = Math.min(minV, num);
//            }
//        }
        return minV;
    }

    @Test
    public void test() {
        int[] nums = {2, 5};
        destroyTargets(nums, 2);
    }
}
