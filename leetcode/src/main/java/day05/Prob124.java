package day05;

import common.TreeNode;

/**
 * @description 二叉树中的最大路径和
 * @date:2022/8/28 12:51
 * @author: qyl
 */
public class Prob124 {
    public static void main(String[] args) {
        TreeNode left = new TreeNode(2, null, null);
        TreeNode right = new TreeNode(3, null, null);
        TreeNode root = new TreeNode(1, left, right);
        System.out.println(new Solution124().maxPathSum(root));
    }
}

class Solution124 {
    int res = -Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        maxGain(root);

        return res;
    }

    int maxGain(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftSon = Math.max(maxGain(root.left), 0), rightSon = Math.max(maxGain(root.right), 0);
        res = Math.max(res,root.val + leftSon + rightSon);
        return root.val += Math.max(leftSon, rightSon);
    }
}
