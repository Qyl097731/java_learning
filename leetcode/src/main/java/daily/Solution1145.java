package daily;

import common.TreeNode;

/**
 * @description
 * @date: 2023/2/3 18:40
 * @author: qyl
 */
public class Solution1145 {
    TreeNode node;

    public boolean btreeGameWinningMove(TreeNode root, int n, int x) {
        find (root, x);
        int leftSize = size (root.left);
        int rightSize = size (root.right);
        int curSize = leftSize + rightSize + 1;
        return leftSize > n / 2
                || rightSize > n / 2
                || curSize <= n / 2;
    }

    void find(TreeNode root, int x) {
        if (root == null) {
            return;
        }
        if (root.val == x) {
            this.node = root;
            return;
        }
        find (root.left, x);
        find (root.right, x);
    }

    int size(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + size (root.left) + size (root.right);
    }
}
