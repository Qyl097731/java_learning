package day09;

import common.TreeNode;

import java.util.LinkedList;
import java.util.List;

/**
 * @description 二叉树的中序遍历
 * @date:2022/9/3 21:56
 * @author: qyl
 */
public class Pro94 {
    public static void main(String[] args) {
        TreeNode right =  new TreeNode(2,new TreeNode(3,null,null),null);
        TreeNode root = new TreeNode(1,null,right);
        System.out.println(new Solution94().inorderTraversal(root));
    }
}
class Solution94 {
    public List<Integer> inorderTraversal(TreeNode root) {
        return pre(root);
    }
    public List<Integer> pre(TreeNode node){
        List<Integer> ret = new LinkedList<>();
        if (node == null) {
            return ret ;
        }
        if (node.left != null){
            ret.addAll(pre(node.left));
        }
        ret.add(node.val);
        if (node.right != null){
            ret.addAll(pre(node.right));
        }
        return ret;
    }
}
