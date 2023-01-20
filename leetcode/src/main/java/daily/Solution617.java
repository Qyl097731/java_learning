package daily;

import common.TreeNode;

/**
 * @description
 * @date:2023/1/20 20:39
 * @author: qyl
 */
public class Solution617 {
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }
        return new TreeNode (
                root1.val + root2.val,
                mergeTrees (root1.left, root2.left),
                mergeTrees (root1.right, root2.right));
    }
}
