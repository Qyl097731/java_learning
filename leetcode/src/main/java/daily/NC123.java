package daily;

import common.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description
 * @date 2023/3/11 15:06
 * @author: qyl
 */
public class NC123 {
    String s = "";

    public TreeNode deserialize(String data) {
        return rcode (new ArrayList<> (Arrays.asList (data.split (","))));
    }

    public void code(TreeNode root) {
        if (root == null) {
            s += "None,";
        } else {
            s += root.val + ",";
            code (root.left);
            code (root.right);
        }
    }

    public TreeNode rcode(List<String> data) {
        if ("None".equals (data.get (0))) {
            data.remove (0);
            return null;
        }
        TreeNode treeNode = new TreeNode (Integer.parseInt (data.get (0)));
        data.remove (0);
        treeNode.left = rcode (data);
        treeNode.right = rcode (data);
        return treeNode;
    }
}
