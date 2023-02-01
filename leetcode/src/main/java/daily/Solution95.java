package daily;

import common.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @date:2023/1/31 23:25
 * @author: qyl
 */
public class Solution95 {
    public List<TreeNode> generateTrees(int n) {
        return generateTrees (1, n);
    }

    private List<TreeNode> generateTrees(int start, int end) {
        List<TreeNode> allTrees = new ArrayList<> ();
        if (start > end) {
            allTrees.add (null);
            return allTrees;
        }
        for (int i = start; i <= end; i++) {
            List<TreeNode> ltree = generateTrees (start, i - 1);
            List<TreeNode> rtree = generateTrees (i + 1, end);
            for (TreeNode l : ltree) {
                for (TreeNode r : rtree) {
                    TreeNode curtree = new TreeNode (i);
                    curtree.left = l;
                    curtree.right = r;
                    allTrees.add (curtree);
                }
            }
        }
        return allTrees;
    }
}
