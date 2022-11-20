package day48;

import common.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

/**
 * @description
 * @date:2022/11/20 10:28
 * @author: qyl
 */
public class Solution6242 {

    List<List<Integer>> res = new ArrayList<> ( );
    TreeSet<Integer> set = new TreeSet<> ( );
    List<Integer> nums = new ArrayList<> ( );

    public List<List<Integer>> closestNodes(TreeNode root, List<Integer> queries) {
//        return solveA (root,queries);
        return solveB (root, queries);
    }

    private List<List<Integer>> solveB(TreeNode root, List<Integer> queries) {
        build (root);
        int n = nums.size ( );
        for (int query : queries) {
            res.add (Arrays.asList (findMax (query, n), findMin (query, n)));
        }
        return res;
    }

    private int findMax(int num, int n) {
        int l = 0, r = n - 1, mid;
        int ans = -1;
        while (l <= r) {
            mid = (l + r + 1) >> 1;
            if (nums.get (mid) <= num) {
                ans = nums.get (mid);
                l = mid + 1;
            } else if (nums.get (mid) > num) {
                r = mid - 1;
            }
        }
        return ans;
    }

    private int findMin(int num, int n) {
        int l = 0, r = n - 1, mid;
        int ans = -1;
        while (l <= r) {
            mid = l + r >> 1;
            if (nums.get (mid) >= num) {
                ans = nums.get (mid);
                r = mid - 1;
            } else if (nums.get (mid) < num) {
                l = mid + 1;
            }
        }
        return ans;
    }

    private void build(TreeNode root) {
        if (root == null) {
            return;
        }
        build (root.left);
        nums.add (root.val);
        build (root.right);
    }

    private List<List<Integer>> solveA(TreeNode root, List<Integer> queries) {
        dfs (root);
        for (int query : queries) {
            res.add (Arrays.asList (set.floor (query) == null ? -1 :
                    set.floor (query), set.ceiling (query) == null ? -1 : set.ceiling (query)));
        }
        return res;
    }

    private void dfs(TreeNode root) {
        set.add (root.val);
        if (root.left != null) {
            dfs (root.left);
        }
        if (root.right != null) {
            dfs (root.right);
        }
    }
}
