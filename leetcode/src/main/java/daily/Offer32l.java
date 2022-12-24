package daily;

import common.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @description
 * @date:2022/12/24 14:37
 * @author: qyl
 */
public class Offer32l {
    public int[] levelOrder(TreeNode root) {
        if (root == null) {
            return null;
        }
        List<Integer> res = new ArrayList<> ( );
        Queue<TreeNode> q = new ArrayDeque<TreeNode> ( ) {{
            offer (root);
        }};
        while (!q.isEmpty ( )) {
            TreeNode cur = q.poll ( );
            res.add (cur.val);
            if (cur.left != null) {
                q.offer (cur.left);
            }
            if (cur.right != null) {
                q.offer (cur.right);
            }
        }
        return res.stream ( ).mapToInt (i -> i).toArray ( );
    }
}
