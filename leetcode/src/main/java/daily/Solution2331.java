package daily;

import common.TreeNode;

/**
 * @description
 * @date:2022/12/20 17:12
 * @author: qyl
 */
public class Solution2331 {
    public boolean evaluateTree(TreeNode root) {
        if (root == null) {
            return false;
        }
        return dfs (root);
    }

    private boolean dfs(TreeNode root) {
        if (root.val == 2) {
            return dfs (root.left) || dfs (root.right);
        } else if (root.val == 3) {
            return dfs (root.left) && dfs (root.right);
        } else {
            return root.val == 1;
        }
    }
}
