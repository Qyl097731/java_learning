package daily;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @description
 * @date:2023/1/13 22:41
 * @author: qyl
 */
public class Solution2342 {
    public int maximumSum(int[] nums) {
        Map<Integer, PriorityQueue<Integer>> map = new HashMap<> ();
        for (int num : nums) {
            int sum = compute (num);
            map.computeIfAbsent (sum, k -> new PriorityQueue<> ((o1, o2) -> -o1.compareTo (o2))).add (num);
        }
        int res = -1;
        for (Map.Entry<Integer, PriorityQueue<Integer>> entry : map.entrySet ()) {
            PriorityQueue<Integer> values = entry.getValue ();
            if (values.size () > 1) {
                res = Math.max (res, values.poll () + values.poll ());
            }
        }
        return res;
    }

    private int compute(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }

    @Test
    public void test() {
        maximumSum (new int[]{229, 398, 269, 317, 420, 464, 491, 218, 439, 153, 482, 169, 411, 93, 147, 50, 347, 210, 251, 366, 401});
    }
}
