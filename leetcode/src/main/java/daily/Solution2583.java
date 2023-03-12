package daily;

import common.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * @description
 * @date 2023/3/12 0:48
 * @author: qyl
 */
public class Solution2583 {
    public long kthLargestLevelSum(TreeNode root, int k) {
        long res = 0;
        ArrayList<Long> nums = new ArrayList<> ();
        if (root == null) {
            return res;
        }
        ArrayDeque<TreeNode> oldQ = new ArrayDeque<> (), newQ;
        oldQ.offerLast (root);
        while (!oldQ.isEmpty ()) {
            long temp = 0;
            newQ = new ArrayDeque<> ();
            while (!oldQ.isEmpty ()) {
                TreeNode node = oldQ.pollFirst ();
                if (node.left != null) {
                    newQ.offerLast (node.left);
                }
                if (node.right != null) {
                    newQ.offerLast (node.right);
                }
                temp += node.val;
            }
            oldQ = newQ;
            nums.add (temp);
        }
        nums.sort ((a, b) -> Long.compare (b, a));
        return nums.size () >= k ? nums.get (k - 1) : -1;
    }
}
