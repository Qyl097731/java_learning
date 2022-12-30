package daily;

import common.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @description
 * @date:2022/12/30 18:05
 * @author: qyl
 */
public class Offer26 {
    TreeNode root;

    public boolean isSubStructure(TreeNode A, TreeNode B) {
        return (A != null && B != null) && (isSame (A, B) || isSubStructure (A.left, B) || isSubStructure (A.right, B));
    }

    private boolean isSame(TreeNode A, TreeNode B) {
        if (B == null) {
            return true;
        }
        if (A == null) {
            return false;
        }
        return isSame (A.left, B.left) && isSame (A.right, B.right) && A.val == B.val;
    }

    @Test
    public void test() {
        TreeNode A = TreeNode.build (Arrays.asList (4, 2, 3, 4, 5, 6, 7, 8, 9), 1, 9);
        TreeNode B = TreeNode.build (Arrays.asList (4, 8, 9), 1, 3);
        isSubStructure (A, B);
    }
}
