package leetcode;

import java.util.Arrays;

/**
 * projectName:  fushi
 * packageName: leetcode
 * date: 2022-03-27 23:30
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Pro31 {
    public static void main(String[] args) {
        new Solution31().nextPermutation(new int[]{
                1,2,3
        });
    }
}

class Solution31 {
    public void nextPermutation(int[] nums) {
        int len = nums.length;
        int l,r;
        boolean flag = false;
        for(l = len-1;l > 0;l--){
            if(nums[l-1] < nums[l]){
                flag = true;
                break;
            }
        }

        if(!flag){
            Arrays.sort(nums);
        }else{
            l--;
            for(r = len - 1; r > 0 ; r--){
                if(nums[r] > nums[l]){
                    int temp;
                    temp = nums[l];
                    nums[l] = nums[r];
                    nums[r] = temp;
                    break;
                }
            }
        }
        Arrays.sort(nums,l+1,len);
        for (int num:nums){
            System.out.println(num);
        }
    }
}


// 1 3 2
// 2 3 1
