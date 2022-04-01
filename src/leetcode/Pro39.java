package leetcode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * projectName:  fushi
 * packageName: leetcode
 * date: 2022-03-28 15:47
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Pro39 {
    public static void main(String[] args) {
        List<List<Integer>> lists = new Solution39().combinationSum(new int[]{2, 3, 6, 7}, 7);
        System.out.println(Arrays.toString(lists.toArray()));
    }
}
class Solution39 {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>>combination = new ArrayList<>();
        List<Integer>nums = new ArrayList<>();
        Arrays.sort(candidates);
        int len = candidates.length;
        for(int i = 0 ; i < len;i++){
            nums.add(candidates[i]);
            dfs(combination,nums,target-candidates[i],i,len,candidates,1);
            nums.remove(0);
        }
        return combination;
    }

    void dfs(List<List<Integer>>combination,List<Integer>nums,int target,int index,int len,int[] candidates,int numLen){
        if(target == 0){
            combination.add(new ArrayList<>(nums));
            return;
        }
        if(target < 0){
            return;
        }
        for(int i = index; i < len && target - candidates[i] >= 0;i++){
            nums.add(candidates[i]);
            dfs(combination,nums,target-candidates[i],i,len,candidates,numLen+1);
            nums.remove(numLen);
        }
    }
}
