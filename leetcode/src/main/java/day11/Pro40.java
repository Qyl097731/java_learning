package day11;

import java.util.*;

/**
 * @description
 * @date:2022/9/28 13:31
 * @author: qyl
 */
public class Pro40 {
    public static void main(String[] args) {
        System.out.println(new Solution40().combinationSum2(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, 30));
    }
}

class Solution40 {
    List<List<Integer>> res = new ArrayList<>();
    List<int[]> nums = new ArrayList<>();
    List<Integer> temp = new ArrayList<>();
    int len;

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);

        len = candidates.length;

        for (int i = 0; i < len; i++) {
            int size = nums.size();
            if (nums.isEmpty() || candidates[i] != nums.get(size - 1)[0]) {
                nums.add(new int[]{candidates[i], 1});
            } else {
                nums.get(size - 1)[1]++;
            }
        }

        dfs(0, target);

        return res;
    }

    private void dfs(int idx, int target) {
        if (target == 0) {
            res.add(new ArrayList<>(temp));
            return;
        }
        if (idx == nums.size() || nums.get(idx)[0] > target) {
            return;
        }


        dfs(idx + 1, target);

        int most = Math.min(target / nums.get(idx)[0], nums.get(idx)[1]);
        for (int i = 1; i <= most; i++) {
            temp.add(nums.get(idx)[0]);
            dfs(idx+1, target - nums.get(idx)[0] * i);
        }

        for (int i = 0; i < most; i++) {
            temp.remove(temp.size() - 1);
        }
    }
}
