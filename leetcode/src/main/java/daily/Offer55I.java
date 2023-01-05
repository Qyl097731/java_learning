package daily;

import common.TreeNode;

/**
 * @description
 * @date:2023/1/5 0:03
 * @author: qyl
 */
public class Offer55I {
    int res = 0;

    public int maxDepth(TreeNode root) {
//        return solve1(root);
        return solve2 (root);
    }

    private int solve2(TreeNode root) {
        return root == null ? 0 : Math.max (solve2 (root.left), solve2 (root.right)) + 1;
    }

    private int solve1(TreeNode root) {
        dfs (root, 0);
        return res;
    }

    private void dfs(TreeNode root, int dept) {
        if (root == null) {
            return;
        }
        dept++;
        res = Math.max (res, dept);
        if (root.left != null) {
            dfs (root.left, dept);
        }
        if (root.right != null) {
            dfs (root.right, dept);
        }
    }
}
