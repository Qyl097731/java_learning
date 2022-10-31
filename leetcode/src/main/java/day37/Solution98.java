package day37;

import common.TreeNode;

/**
 * @description
 * @date:2022/10/31 14:53
 * @author: qyl
 */
public class Solution98 {
    long maxValue = Long.MIN_VALUE;
    public boolean isValidBST(TreeNode root) {
        boolean res;
        if (root == null){
            return true;
        }
        if (root.left != null){
            res = isValidBST(root.left);
            if (!res) return false;
        }
        if (maxValue < root.val){
            maxValue = root.val;
        }else {
            return false;
        }
        if (root.right != null){
            res = isValidBST(root.right);
            if (!res) return false;
        }
        return true;
    }
}
