package day44;

import common.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @date:2022/11/13 10:27
 * @author: qyl
 */
public class Solution6235 {

    Map<Integer, List<Integer>> map = new HashMap<>();
    int dep = 0;

    public int minimumOperations(TreeNode root) {
        int res = 0;
//        dfs(root, 0);
        map.put(0, Arrays.asList(1));
        map.put(1, Arrays.asList(4, 3));
        map.put(2, Arrays.asList(7, 6, 8, 5));
        map.put(3, Arrays.asList(9, 10));

        for (int i = 0; i <= 3; i++) {
            List<Integer> list = map.get(i);
            Integer[] array = list.toArray(new Integer[0]);
            Map<Integer, Integer> map1 = new HashMap<>();
            int n = list.size();
            for (int j = 0; j < n; j++) {
                map1.put(list.get(j), j);
            }
            list.sort(Integer::compareTo);
            res += solve(list, map1, array);
        }
        return res;
    }

    private int solve(List<Integer> list, Map<Integer, Integer> map1, Integer[] array) {
        int res = 0;
        int n = list.size();
        for (int i = 0; i < n; i++) {
            if (list.get(i).compareTo(array[i]) == 0) continue;
            int j = map1.get(list.get(i));
            map1.put(array[i], j);
            int temp = array[j];
            array[j] = array[i];
            array[i] = temp;
            res++;

        }
        return res;
    }

    private void dfs(TreeNode root, int dept) {
        if (root == null) {
            return;
        }
        dep = Math.max(dept, dep);
        if (!map.containsKey(dept)) {
            ArrayList<Integer> list = new ArrayList<>();
            list.add(root.val);
            map.put(dept, list);
        } else {
            List<Integer> list = map.get(dept);
            list.add(root.val);
            map.put(dept, list);
        }
        if (root.left != null) {
            dfs(root.left, dept + 1);
        }
        if (root.right != null) {
            dfs(root.right, dept + 1);
        }
    }

    @Test
    public void test() {
        minimumOperations(null);
    }
}
