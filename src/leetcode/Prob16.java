package leetcode;

import java.util.Arrays;

/**
 * projectName:  fushi
 * packageName: leetcode
 * date: 2022-03-26 00:37
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
class Solution16 {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int res = nums[0] + nums[1] + nums[2];
        for(int i = 0 ; i < nums.length - 2 ; i++){
            int l = i+1;
            int r = nums.length -1;
            while(l < r){
                int sum = nums[i] + nums[l] + nums[r] ;
                if(sum == target){
                    return sum;
                }else if(sum < target){
                    l++;
                }else{
                    r--;
                }
                if(Math.abs(sum-target) < Math.abs(res-target)){
                    res = sum;
                }
            }
        }
        return res;
    }
}
