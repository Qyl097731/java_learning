package daily;

import java.util.*;

/**
 * @description
 * @date:2023/1/18 15:01
 * @author: qyl
 */
public class Solution347 {
    public int[] topKFrequent(int[] nums, int k) {
//        return solve1(nums,k);
        return solve2 (nums, k);
    }

    private int[] solve2(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<> ();
        for (int num : nums) {
            map.put (num, map.getOrDefault (num, 0) + 1);
        }
        PriorityQueue<int[]> queue = new PriorityQueue<> (Comparator.comparingInt (a -> a[1]));
        for (Map.Entry<Integer, Integer> entry : map.entrySet ()) {
            int num = entry.getKey (), count = entry.getValue ();
            if (queue.size () == k) {
                if (queue.peek ()[1] < count) {
                    queue.poll ();
                    queue.offer (new int[]{num, count});
                }
            } else {
                queue.offer (new int[]{num, count});
            }
        }
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = queue.poll ()[0];
        }
        return res;
    }

    private int[] solve1(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<> ();
        for (int num : nums) {
            map.put (num, map.getOrDefault (num, 0) + 1);
        }
        List<Map.Entry<Integer, Integer>> entries = new ArrayList<> (map.entrySet ());
        Collections.sort (entries, (a, b) -> -Integer.compare (a.getValue (), b.getValue ()));
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = entries.get (i).getKey ();
        }
        return res;
    }
}
