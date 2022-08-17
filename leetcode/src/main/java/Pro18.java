package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * projectName:  fushi
 * packageName: leetcode
 * date: 2022-03-27 02:06
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Pro18 {
}

class Solution18 {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> arry;
        Arrays.sort(nums);
        int len = nums.length;
        for (int i = 0; i < len - 3; i++) {
            if(i > 0 && nums[i] == nums[i-1]){
                continue;
            }
            for (int j = i + 1; j < len - 2; j++) {
                if(j > i + 1 && nums[j] == nums[j-1]){
                    continue;
                }

                int temp = target - nums[i] - nums[j];
                int k = j + 1, l = len - 1;

                while (k < l) {
                    int sum = nums[k] + nums[l];
                    if (sum == temp) {
                        arry = new ArrayList<>(Arrays.asList(nums[i], nums[j], nums[k], nums[l]));
                        res.add(arry);
                        while(k < l && nums[k+1] == nums[k]) {
                            k++;
                        }
                        while(k < l && nums[l-1] == nums[l]) {
                            l--;
                        }
                        k++;l--;
                    }else if(sum > temp){
                        l--;
                    }else{
                        k++;
                    }
                }
            }
        }
        return res;
    }
}
