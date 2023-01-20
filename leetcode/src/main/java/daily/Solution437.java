package daily;

import common.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @date:2023/1/20 15:52
 * @author: qyl
 */
public class Solution437 {
    Map<Long, Integer> count = new HashMap<> ();
    int res = 0;

    public int pathSum(TreeNode root, long targetSum) {
//        return solve1(root,targetSum);
        return solve2 (root, targetSum);
    }

    private int solve2(TreeNode root, long targetSum) {
        count.put (0L, 1);
        dfs (root, targetSum, 0L);
        return res;
    }

    private void dfs(TreeNode root, long targetSum, long sum) {
        if (root == null) {
            return;
        }
        long cur = root.val + sum;

        res += count.getOrDefault (cur - targetSum, 0);

        count.put (sum, count.getOrDefault (cur, 0) + 1);
        dfs (root.left, targetSum, cur);
        dfs (root.right, targetSum, cur);
        count.put (sum, count.getOrDefault (cur, 0) - 1);
    }

    private int solve1(TreeNode root, long targetSum) {
        if (root == null) {
            return 0;
        }
        return rootSum (root, targetSum) + pathSum (root.left, targetSum) + pathSum (root.right, targetSum);
    }

    private int rootSum(TreeNode root, long targetSum) {
        if (root == null) {
            return 0;
        }
        return rootSum (root.left, targetSum - root.val) + rootSum (root.right, targetSum - root.val) + targetSum == root.val ? 1 : 0;
    }
}
