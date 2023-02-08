package daily;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @description
 * @date 2023/2/8 18:34
 * @author: qyl
 */
public class Solution2248 {
    public List<Integer> intersection(int[][] nums) {
        List<Integer> res = new ArrayList<> ();
        int n = nums.length;
        int[] array = nums[0];
        for (int key : array) {
            int cnt = 0;
            for (int i = 1; i < n; i++) {
                int[] other = nums[i];
                for (int j = 0; j < other.length; j++) {
                    if (key == other[j]) {
                        cnt++;
                        break;
                    }
                }
            }
            if (cnt == n - 1) {
                res.add (key);
            }
        }
        Collections.sort (res);
        return res;
    }
}
