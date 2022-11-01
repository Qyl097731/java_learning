package day38;

import common.TreeNode;
import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/11/1 17:11
 * @author: qyl
 */
public class Solution105 {
    int cur = 0;
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return build(preorder, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    TreeNode build(int[] preorder, int pr, int[] inorder, int il, int ir) {
        if (cur > pr || il > ir) {
            return null;
        }
        int idx = findRootIndex(preorder[cur], inorder, il, ir);
        if (idx == -1) {
            return null;
        }
        cur++;
        TreeNode left = build(preorder, pr, inorder, il, idx - 1);
        TreeNode right = build(preorder, pr, inorder, idx + 1, ir);
        return new TreeNode(inorder[idx], left,right);
    }

    private int findRootIndex(int root, int[] inorder, int l, int r) {
        for (int i = l; i <= r; i++) {
            if (root == inorder[i]) {
                return i;
            }
        }
        return -1;
    }

    @Test
    public void test() {
        System.out.println(buildTree(new int[]{3, 9, 20, 15, 7}, new int[]{9, 3, 15, 20, 7}));
    }
}
