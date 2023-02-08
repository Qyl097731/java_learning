package daily;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * @description
 * @date 2023/2/8 18:57
 * @author: qyl
 */
public class Solution2251 {
    public int[] fullBloomFlowers(int[][] flowers, int[] persons) {
        TreeSet<Integer> set = new TreeSet<> ();
        Map<Integer, Integer> map = new HashMap<> ();
        int n = persons.length;
        int[] res = new int[n];
        for (int[] flower : flowers) {
            int start = flower[0], end = flower[1];
            set.add (start);
            set.add (end);
        }
        for (int person : persons) {
            set.add (person);
        }
        int idx = 0;
        for (int num : set) {
            map.put (num, idx++);
        }
        int size = set.size ();
        int[] nums = new int[size + 1];
        for (int[] flower : flowers) {
            int start = map.get (flower[0]), end = map.get (flower[1]);
            nums[start]++;
            nums[end + 1]--;
        }
        for (int i = 1; i < size + 1; i++) {
            nums[i] += nums[i - 1];
        }
        for (int i = 0; i < n; i++) {
            res[i] = nums[map.get (persons[i])];
        }
        return res;
    }
}
