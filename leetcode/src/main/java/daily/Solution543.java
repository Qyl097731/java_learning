package daily;

import common.TreeNode;

/**
 * @description
 * @date:2023/1/20 20:59
 * @author: qyl
 */
public class Solution543 {
    int res = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        depth (root);
        return res;
    }

    private int depth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int l = depth (root.left), r = depth (root.right);
        res = Math.max (l + r + 1, res);
        return Math.max (l, r) + 1;
    }
}
