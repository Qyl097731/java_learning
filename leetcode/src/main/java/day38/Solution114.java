package day38;

import common.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @description
 * @date:2022/11/1 18:22
 * @author: qyl
 */
public class Solution114 {
    Deque<TreeNode> queue = new ArrayDeque<>();
    public void flatten(TreeNode root) {
        preOrder(root);
        if (queue.size() <= 1){
            return;
        }
        TreeNode pre = queue.pollFirst(),cur;
        while(!queue.isEmpty()){
            cur = queue.pollFirst();
            pre.left= null;
            pre.right = cur;
            pre = cur;
        }
    }

    private void preOrder(TreeNode root) {
        if (root == null) return ;
        queue.offerLast(root);
        if (root.left != null){
            preOrder(root.left);
        }
        if(root.right != null){
            preOrder(root.right);
        }
    }
}
