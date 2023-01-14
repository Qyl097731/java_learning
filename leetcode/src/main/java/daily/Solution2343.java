package daily;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

/**
 * @description
 * @date:2023/1/14 17:15
 * @author: qyl
 */
public class Solution2343 {
    public int[] smallestTrimmedNumbers(String[] nums, int[][] queries) {
        int q = queries.length, m = nums[0].length ();
        int[] res = new int[q];
        for (int k = 0; k < queries.length; k++) {
            int[] query = queries[k];
            Integer[] idx = IntStream.range (0, nums.length).boxed ().toArray (Integer[]::new);
            Arrays.sort (idx, Comparator.comparing (i -> nums[i].substring (m - query[1])));
            res[k] = idx[query[0] - 1];
        }
        return res;
    }
}
