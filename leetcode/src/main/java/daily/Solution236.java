package daily;

import common.TreeNode;

/**
 * @description
 * @date:2023/1/14 19:20
 * @author: qyl
 */
public class Solution236 {
    TreeNode res = null;

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        isParent (root, p, q);
        return res;
    }

    private boolean isParent(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return false;
        }
        boolean lson = isParent (root.left, p, q);
        boolean rson = isParent (root.right, p, q);
        if ((lson && rson) || ((root.val == p.val || root.val == q.val) && (lson || rson))) {
            res = root;
        }
        return lson || rson || (root.val == p.val || root.val == q.val);
    }
}
