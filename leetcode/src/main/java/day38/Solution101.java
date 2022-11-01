package day38;

import common.TreeNode;

/**
 * @description
 * @date:2022/11/1 16:33
 * @author: qyl
 */
public class Solution101 {
    StringBuilder preOrder = new StringBuilder();
    StringBuilder postOrder = new StringBuilder();

    public boolean isSymmetric(TreeNode root) {
        preOrder(root);
        postOrder(root);
        int n = postOrder.length();
        for (int i = 0; i < n; i++) {
            if (preOrder.charAt(i) != postOrder.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    private void postOrder(TreeNode root) {
        if (root == null) {postOrder.append("n");return;}
        postOrder.append(root.val);
        postOrder(root.right);
        postOrder(root.left);
    }
    private void preOrder(TreeNode root) {
        if (root == null) {preOrder.append("n");return;}
        preOrder.append(root.val);
        preOrder(root.left);
        preOrder(root.right);
    }
}
