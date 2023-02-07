package daily;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description
 * @date 2023/2/7 22:51
 * @author: qyl
 */
public class Solution77 {
    List<List<Integer>> res = new ArrayList<> ();
    Integer[] array;

    public List<List<Integer>> combine(int n, int k) {
        array = new Integer[k];
        dfs (n, k, 0, 0);
        return res;
    }

    private void dfs(int n, int k, int len, int pre) {
        if (len == k) {
            res.add (Arrays.stream (array).collect (Collectors.toList ()));
            return;
        }
        for (int i = pre + 1; i <= n; i++) {
            array[len] = i;
            dfs (n, k, len + 1, i);
        }
    }
}
