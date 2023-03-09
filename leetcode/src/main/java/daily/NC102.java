package daily;

import common.TreeNode;

/**
 * @description
 * @date 2023/3/9 20:14
 * @author: qyl
 */
public class NC102 {
    int res = -1;

    public int lowestCommonAncestor(TreeNode root, int o1, int o2) {
        // write code here
        find (root, o1, o2);
        return res;
    }

    private boolean find(TreeNode root, int o1, int o2) {
        if (root == null) {
            return false;
        }
        if (res != -1) {
            return false;
        }
        boolean left = find (root.left, o1, o2);
        boolean right = find (root.right, o1, o2);
        if ((root.val == o1 || root.val == o2) && (left || right)) {
            res = root.val;
            return true;
        }
        if (left && right) {
            res = root.val;
            return true;
        }
        return root.val == o1 || root.val == o2;
    }
}
