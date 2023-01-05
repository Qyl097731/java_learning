package daily;

import common.TreeNode;
import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2023/1/5 15:29
 * @author: qyl
 */
public class Offer07 {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return build (preorder, inorder, 0, preorder.length, 0, inorder.length);
    }

    private TreeNode build(int[] preorder, int[] inorder, int pstart, int pend, int start, int end) {
        if (pstart >= pend) {
            return null;
        }
        TreeNode node = new TreeNode (preorder[pstart]);
        int i;
        for (i = start; i < end; i++) {
            if (inorder[i] == preorder[pstart]) {
                break;
            }
        }
        node.left = build (preorder, inorder, pstart + 1, pstart + i - start + 1, start, i);
        node.right = build (preorder, inorder, pstart + i - start + 1, pend, i + 1, end);
        return node;
    }

    @Test
    public void test() {
        int[] pre = {3, 9, 20, 15, 7};
        int[] in = {9, 3, 15, 20, 7};
        buildTree (pre, in);
    }
}
