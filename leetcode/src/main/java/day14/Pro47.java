package day14;

import java.util.*;

/**
 * @description
 * @date:2022/10/4 10:33
 * @author: qyl
 */
public class Pro47 {
    public static void main(String[] args) {
        System.out.println(new Solution47().permuteUnique(new int[]{1, 1, 2}));
    }
}

class Solution47 {
    List<List<Integer>> res = new ArrayList<>();
    boolean[] used;

    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        int len = nums.length;
        used = new boolean[len];
        dfs(0, new ArrayList<>(), len, nums);
        return res;
    }

    private void dfs(int len, ArrayList<Integer> temp, int target, int[] nums) {
        if (len == target) {
            res.add(new ArrayList<>(temp));
            return;
        }
        for (int i = 0; i < target; i++) {
            if (used[i] || (i > 0 && nums[i - 1] == nums[i] && !used[i - 1])) {
                continue;
            }
            used[i] = true;
            temp.add(nums[i]);
            dfs(len + 1, temp, target, nums);
            temp.remove(len);
            used[i] = false;
        }
    }
}
