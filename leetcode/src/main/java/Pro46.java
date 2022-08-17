package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * projectName:  fushi
 * packageName: leetcode
 * date: 2022-03-28 16:20
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Pro46 {
}
class Solution46 {
    public List<List<Integer>> permute(int[] nums) {
        int len = nums.length;
        Integer[] res = new Integer[len];
        boolean[] mark = new boolean[len];
        List<List<Integer>>resList = new ArrayList<>();
        dfs(resList,res,mark,0,len,nums);
        return resList;
    }
    void dfs(List<List<Integer>>resList,Integer[] res,boolean[] mark,int curLen,int len,int[] nums){
        if(curLen == len){
            resList.add(new ArrayList<>(Arrays.asList(res)));
            return;
        }
        for(int i = 0 ; i < len ; i++){
            if(!mark[i]){
                mark[i] = true;
                res[curLen] = nums[i];
                dfs(resList,res,mark,curLen+1,len,nums);
                mark[i] = false;
            }
        }
    }
}
