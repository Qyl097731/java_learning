package daily;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description
 * @date:2023/1/18 17:41
 * @author: qyl
 */
public class Solution406 {
    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort (people, (p1, p2) -> {
            if (p1[0] == p2[0]) {
                return p1[1] - p2[1];
            }
            return p2[0] - p1[0];
        });
        List<int[]> ans = new ArrayList<> ();
        for (int[] person : people) {
            ans.add (person[1], person);
        }
        return ans.toArray (new int[ans.size ()][]);
    }
}
