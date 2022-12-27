package daily;

import common.TreeNode;

import java.util.ArrayDeque;

/**
 * @description
 * @date:2022/12/27 19:47
 * @author: qyl
 */
public class Offer27 {
    public TreeNode mirrorTree(TreeNode root) {
        if (root == null) {
            return root;
        }
//        return solve1(root);
        solve2 (root);
        return root;
    }

    private void solve2(TreeNode root) {
        if (root.right != null) {
            solve2 (root.right);
        }
        if (root.left != null) {
            solve2 (root.left);
        }
        TreeNode t = root.left;
        root.left = root.right;
        root.right = t;
    }

    private TreeNode solve1(TreeNode root) {
        ArrayDeque<TreeNode> queue = new ArrayDeque<> ();
        queue.add (root);
        while (!queue.isEmpty ()) {
            ArrayDeque<TreeNode> temp = new ArrayDeque<> ();
            while (!queue.isEmpty ()) {
                TreeNode node = queue.pollFirst ();
                if (node.right != null) {
                    temp.offerLast (node.right);
                }
                if (node.left != null) {
                    temp.offerLast (node.left);
                }
                TreeNode t = node.left;
                node.left = node.right;
                node.right = t;
            }
            queue.addAll (temp);
        }

        return root;
    }

}
