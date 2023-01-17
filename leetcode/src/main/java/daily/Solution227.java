package daily;

import common.TreeNode;

import java.util.Arrays;

/**
 * @description
 * @date:2023/1/17 21:18
 * @author: qyl
 */
public class Solution227 {

    public int rob(TreeNode root) {
        int[] f = dfs (root);
        return Math.max (f[0], f[1]);
    }

    private int[] dfs(TreeNode root) {
        if (root == null) {
            return new int[]{0, 0};
        }
        int[] left = dfs (root.left);
        int[] right = dfs (root.right);

        return new int[]{
                root.val + left[1] + right[1], Arrays.stream (left).max ().getAsInt () + Arrays.stream (right).max ().getAsInt ()};
    }
}
