package daily;

import common.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @description
 * @date:2023/1/20 19:56
 * @author: qyl
 */
public class Solution226 {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        return new TreeNode (root.val, invertTree (root.right), invertTree (root.left));
    }

    @Test
    public void test() {
        TreeNode root = TreeNode.build (Arrays.asList (2, 1, 3), 1, 3);
        TreeNode tree = invertTree (root);
        TreeNode.print (tree);
    }
}
