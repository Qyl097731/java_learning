package daily;

import common.TreeNode;

/**
 * @description
 * @date:2022/12/24 14:58
 * @author: qyl
 */
public class Offer28 {

    public boolean isSymmetric(TreeNode root) {
        return check (root, root);
    }

    private boolean check(TreeNode l, TreeNode r) {
        if (l == null && r == null) {
            return true;
        }
        if (l == null || r == null) return false;
        return l.val == r.val && check (l.left, r.right) && check (l.right, r.left);
    }

}
