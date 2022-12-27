package daily;

import common.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @description
 * @date:2022/12/27 20:06
 * @author: qyl
 */
public class Offere32II {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<> ();

        if (root == null) {
            return res;
        }

        Deque<TreeNode> queue = new ArrayDeque<> ();
        queue.offerLast (root);
        while (!queue.isEmpty ()) {
            int n = queue.size ();
            List<Integer> level = new ArrayList<> ();
            for (int i = 0; i < n; i++) {
                TreeNode node = queue.pollFirst ();
                level.add (node.val);
                if (node.left != null) {
                    queue.offerLast (node.left);
                }
                if (node.right != null) {
                    queue.offerLast (node.right);
                }
            }
            res.add (level);
        }
        return res;
    }
}
