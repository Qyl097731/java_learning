package daily;

import common.TreeNode;

import java.util.*;

/**
 * @description
 * @date:2022/12/27 20:13
 * @author: qyl
 */
public class Offer32III {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<> ();
        if (root == null) {
            return res;
        }

        Deque<TreeNode> queue = new ArrayDeque<> ();
        queue.add (root);
        int cur = 0;
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
            if (cur % 2 == 0) {
                res.add (level);
            } else {
                Collections.reverse (level);
                res.add (level);
            }
            cur++;
        }
        return res;
    }
}
