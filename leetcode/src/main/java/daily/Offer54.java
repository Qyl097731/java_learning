package daily;

import common.TreeNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @description
 * @date:2023/1/4 18:18
 * @author: qyl
 */
public class Offer54 {
    List<Integer> nums = new ArrayList<> ();
    int num = 0;
    int res = 0;

    public int kthLargest(TreeNode root, int k) {
//        return solve1(root,k);
        return solve2 (root, k);
    }

    private int solve2(TreeNode root, int k) {
        mid (root, k);
        return res;
    }

    private void mid(TreeNode root, int k) {
        if (root == null || num == k) {
            return;
        }
        if (root.right != null) {
            mid (root.right, k);
        }
        num++;
        if (num == k) {
            res = root.val;
            return;
        }
        if (root.left != null) {
            mid (root.left, k);
        }
    }

    private int solve1(TreeNode root, int k) {
        pre (root);
        nums.sort (Comparator.reverseOrder ());
        return nums.get (k);
    }

    private void pre(TreeNode root) {
        if (root != null) {
            nums.add (root.val);
        }
        if (root.left != null) {
            pre (root.left);
        }
        if (root.right != null) {
            pre (root.right);
        }
    }
}
