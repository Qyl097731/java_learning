package common;

import java.util.List;

/**
 * @description 树节点
 * @date:2022/8/28 12:54
 * @author: qyl
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public static TreeNode build(List<Integer> nums, int cur, int len) {
        if (cur > len) {
            return null;
        }
        TreeNode node = new TreeNode (nums.get (cur - 1));
        node.left = build (nums, cur * 2, len);
        node.right = build (nums, cur * 2 + 1, len);
        return node;
    }

    public static void print(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.print (root.val + " ");
        print (root.left);
        print (root.right);
    }
}
