package daily;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description
 * @date 2023/2/10 0:01
 * @author: qyl
 */
public class Solution90 {
    List<List<Integer>> res = new ArrayList<> ();
    List<Integer> path = new ArrayList<> ();

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort (nums);
        dfs (0, nums);
        return res;
    }

    private void dfs(int cur, int[] nums) {
        res.add (new ArrayList<> (path));
        for (int i = cur; i < nums.length; i++) {
            if (i > cur && nums[i] == nums[i - 1]) {
                continue;
            }
            path.add (nums[i]);
            dfs (i + 1, nums);
            path.remove (path.size () - 1);
        }
    }
}
