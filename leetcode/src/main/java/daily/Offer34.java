package daily;

import common.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @description
 * @date:2023/1/4 18:38
 * @author: qyl
 */
public class Offer34 {
    List<List<Integer>> res = new ArrayList<> ();

    Deque<Integer> nums = new ArrayDeque<Integer> ();

    public List<List<Integer>> pathSum(TreeNode root, int target) {
        mid (root, target, 0);
        return res;
    }

    private void mid(TreeNode root, int target, int sum) {
        if (root == null) {
            return;
        }
        sum += root.val;
        nums.offerLast (root.val);
        if (root.left == null && root.right == null) {
            if (sum == target) {
                res.add (new ArrayList<> (nums));
            }
        }
        mid (root.left, target, sum);
        mid (root.right, target, sum);
        nums.pollLast ();
    }

}
