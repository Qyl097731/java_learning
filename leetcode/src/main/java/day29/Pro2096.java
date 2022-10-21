package day29;

import common.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @date:2022/10/21 8:20
 * @author: qyl
 */
public class Pro2096 {
    public static void main(String[] args) {
//        TreeNode nodeThree = new TreeNode(3,null,null);
        TreeNode nodeOne = new TreeNode(1,null,null);
//        TreeNode nodeSix = new TreeNode(6,null,null );
//        TreeNode nodeFour = new TreeNode(4,null,null );
        TreeNode nodeTwo = new TreeNode(2,nodeOne,null);

//        TreeNode root = new TreeNode(5,nodeOne,nodeTwo);
        System.out.println(new Solution2096().getDirections(nodeTwo, 2, 1));

    }
}
class Solution2096 {
    TreeNode s = null,t = null;
    Map<TreeNode,TreeNode> g = new HashMap<>();
    public String getDirections(TreeNode root, int startValue, int destValue) {
        findChild(root,startValue,destValue);
        StringBuilder sb ,tb ;
        sb = getPath(s).reverse();
        tb = getPath(t).reverse();
        return getShortestPath(sb,tb);
    }

    private String getShortestPath(StringBuilder sb, StringBuilder tb) {
        int n = sb.length(), m = tb.length(),i = 0;
        int len = Math.min(n,m);
        StringBuilder res = new StringBuilder();
        for (; i < len; i++) {
            if (sb.charAt(i) != tb.charAt(i)) {
                break;
            }
        }
        for (int j = i; j < n; j++) {
            res.append('U');
        }
        res.append(tb, i, tb.length());
        return res.toString();
    }

    private StringBuilder getPath(TreeNode cur) {
        StringBuilder sb = new StringBuilder();
        TreeNode fa = g.get(cur);
        while (fa != null) {
            if (fa.left == cur){
                sb.append('L');
            }else{
                sb.append('R');
            }
            cur = fa;
            fa = g.get(fa);
        }
        return sb;
    }


    private void findChild(TreeNode root, int startValue, int destValue ) {
        if (root.val == startValue){
            s = root;
        }
        if (root.val == destValue){
            t = root;
        }
        if (s != null && t != null){
            return;
        }
        if (root.left != null){
            g.put(root.left,root);
            findChild(root.left,startValue,destValue);
        }
        if (root.right != null){
            g.put(root.right,root);
            findChild(root.right,startValue,destValue);
        }
    }
}
