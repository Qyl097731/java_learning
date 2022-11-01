package day38;

import common.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @description
 * @date:2022/11/1 17:01
 * @author: qyl
 */
public class Solution104 {
    int maxDeep = 0;

    public int maxDepth(TreeNode root) {
//        dfs(root, 1);
        bfs(root);
        return maxDeep;
    }

    private void bfs(TreeNode root) {
        Deque<TreeNode> queue = new ArrayDeque<>();
        int deep = 0;
        queue.offerLast(root);
        while (!queue.isEmpty()) {
            deep++;
            int n = queue.size();
            for (int i = 0; i < n; i++) {
                TreeNode cur = queue.pollFirst();
                if (cur.left != null) {
                    queue.offerLast(cur.left);
                }
                if (cur.right != null) {
                    queue.offerLast(cur.right);
                }
            }
            maxDeep = Math.max(deep,maxDeep);
        }
    }

    private void dfs(TreeNode root, int deep) {
        if (root == null) {
            return;
        }
        maxDeep = Math.max(maxDeep, deep);
        dfs(root.left, deep + 1);
        dfs(root.right, deep + 1);
    }

}
