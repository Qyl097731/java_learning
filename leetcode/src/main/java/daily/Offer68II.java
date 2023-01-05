package daily;

import common.TreeNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @description
 * @date:2023/1/5 0:09
 * @author: qyl
 */
public class Offer68II {
    Map<Integer, TreeNode> fa = new HashMap<> ();
    Set<Integer> visited = new HashSet<> ();
    private TreeNode res = null;

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
//        return solve1(root,p,q);
        return solve2 (root, p, q);
    }

    private TreeNode solve2(TreeNode root, TreeNode p, TreeNode q) {
        buildMap (root);
        while (p != null) {
            visited.add (p.val);
            p = fa.get (p.val);
        }
        while (q != null) {
            if (visited.contains (q.val)) {
                return q;
            }
            q = fa.get (q.val);
        }
        return null;
    }

    private void buildMap(TreeNode root) {
        if (root.left != null) {
            fa.put (root.left.val, root);
            buildMap (root.left);
        }
        if (root.right != null) {
            fa.put (root.right.val, root);
            buildMap (root.right);
        }
    }

    private TreeNode solve1(TreeNode root, TreeNode p, TreeNode q) {
        dfs (root, p, q);
        return res;
    }

    private boolean dfs(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return false;
        }
        boolean lson = dfs (root.left, p, q);
        boolean rson = dfs (root.right, p, q);

        if ((lson && rson) || ((lson || rson) && (root.val == p.val || root.val == q.val))) {
            res = root;
        }
        return lson || rson || (root.val == p.val || root.val == q.val);
    }
}
