package leetcode;

import java.util.*;

/**
 * date: 2022-03-25 19:23
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Pro15 {
    public static void main(String[] args) {
        int[] nums = {-1,-1,0,1,2,-1,-4};
        Solution15 solution = new Solution15();
        List<List<Integer>>res = solution.threeSum(nums);
        System.out.println(res.toString());
    }
}

class Solution15 {
    public List<List<Integer>> threeSum(int[] nums) {
        if(nums.length < 3){
            return new ArrayList<>();
        }
        List<List<Integer>> rest = new ArrayList<>();
        Set<List<Integer>> temp = new HashSet<>();
        Arrays.sort(nums);
        int pt1 , pt2, pt3 ;

        for(pt1 = 0;pt1 < nums.length-2;pt1++){
            pt3 = nums.length - 1;
            for(pt2 = pt1+1;pt2 < pt3 ;) {
                int sum = nums[pt1] + nums[pt2] + nums[pt3];
                if(sum < 0){
                    pt2++;
                }else if(sum > 0){
                    pt3--;
                }else{
                    temp.add(new ArrayList<>(Arrays.asList(nums[pt1],nums[pt2],nums[pt3])));
                    while(pt2 < pt3 && nums[pt2] == nums[pt2+1]) {
                        pt2++;
                    }
                    while(pt2 < pt3 && nums[pt3] == nums[pt3-1]) {
                        pt3--;
                    }
                    pt2++;
                    pt3--;
                }
            }
        }
        for (Iterator<List<Integer>>it = temp.iterator();it.hasNext();){
            rest.add(it.next());
        }
        return rest;
    }
}
