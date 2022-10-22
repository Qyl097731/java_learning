package day30;

/**
 * @description
 * @date:2022/10/22 13:07
 * @author: qyl
 */

import common.TreeNode;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */
public class Pro1080 {
    public static void main(String[] args) {
        TreeNode nodeFive = new TreeNode(-5, null, null);
        TreeNode nodeTwo = new TreeNode(2, nodeFive, null);
        TreeNode nodeFour = new TreeNode(4, null, null);
        TreeNode nodeThree = new TreeNode(-3, nodeFour, null);
        TreeNode nodeOne = new TreeNode(1, nodeTwo, nodeThree);

        TreeNode res = new Solution1080().sufficientSubset(nodeOne, 1);
    }
}

class Solution1080 {
    public TreeNode sufficientSubset(TreeNode root, int limit) {
        if (!dfs(root, limit, 0)) {
            return null;
        }
        return root;
    }

    private boolean dfs(TreeNode cur, int limit, int sum) {
        if (cur.left == null && cur.right == null) {
            return sum + cur.val >= limit;
        }

        boolean flag = false;
        boolean left;
        boolean right;
        if (cur.left != null) {
            left = dfs(cur.left, limit, sum + cur.val);
            if (!left) {
                cur.left = null;
            }
            flag = left;
        }
        if (cur.right != null) {
            right = dfs(cur.right, limit, sum + cur.val);
            if (!right) {
                cur.right = null;
            }
            flag |= right;
        }
        return flag;
    }
}
