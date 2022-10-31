package day37;

import common.TreeNode;

import javax.sound.sampled.Line;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @description
 * @date:2022/10/31 15:10
 * @author: qyl
 */
public class Solution102 {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new LinkedList<>();
        Deque<TreeNode> queue = new ArrayDeque<>();

        if (root == null) {
            return res;
        }
        queue.offerLast(root);
        while (!queue.isEmpty()) {
            List<Integer> level = new LinkedList<>();
            int n = queue.size();
            for (int i = 0; i < n; i++) {
                TreeNode node = queue.pollFirst();
                level.add(node.val);
                if (node.left != null) {
                    queue.offerLast(node.left);
                }
                if (node.right != null) {
                    queue.offerLast(node.right);
                }
            }
            res.add(level);
        }
        return res;
    }
}
