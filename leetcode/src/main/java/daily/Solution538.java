package daily;

import common.TreeNode;

/**
 * @description
 * @date:2023/1/18 16:34
 * @author: qyl
 */
public class Solution538 {
    int sum = 0;

    public TreeNode convertBST(TreeNode root) {
        if (root != null) {
            convertBST (root.right);
            sum += root.val;
            root.val = sum;
            convertBST (root.left);
        }
        return root;
    }
}
