package daily;

import common.TreeNode;

/**
 * @description
 * @date:2023/1/5 16:34
 * @author: qyl
 */
public class Offer55II {
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isBalanced (root.left) && isBalanced (root.right) && Math.abs (height (root.left) - height (root.right)) <= 1;
    }

    private int height(TreeNode root) {
        return root == null ? 0 : Math.max (height (root.left), height (root.right)) + 1;
    }
}
