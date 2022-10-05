package day15;

import common.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description
 * @date:2022/10/5 9:24
 * @author: qyl
 */
public class Pro297 {
    String s = "";

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        code(root);
        return s;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        return rcode(new ArrayList<>(Arrays.asList(data.split(","))));
    }

    public void code(TreeNode root) {
        if (root == null) {
            s += "None,";
        } else {
            s += root.val + ",";
            code(root.left);
            code(root.right);
        }
    }

    public TreeNode rcode(List<String> data) {
        if ("None".equals(data.get(0))) {
            data.remove(0);
            return null;
        }
        TreeNode treeNode = new TreeNode(Integer.parseInt(data.get(0)));
        data.remove(0);
        treeNode.left = rcode(data);
        treeNode.right = rcode(data);
        return treeNode;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2, null, null);
        root.right = null;
        Pro297 pro297 = new Pro297();
        System.out.println(pro297.deserialize(pro297.serialize(root)));
    }
}
