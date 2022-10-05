package day11;

import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @date:2022/9/30 16:00
 * @author: qyl
 */
public class Pro41 {
    public static void main(String[] args) {
        System.out.println(new Solution41().firstMissingPositive(new int[]{3,4,-1,1}));
    }
}

class Solution41 {
    Map<Integer, Integer> pos = new HashMap<>();

    public int firstMissingPositive(int[] nums) {
        int len = nums.length, minNum = 1;
        for (int i = 0; i < len; i++) {
            if (nums[i] > 0 && !pos.containsKey(nums[i])) {
                pos.put(nums[i], i);
            }
        }
        int idx = 0;
        while (idx < len){
            if (!pos.containsKey(minNum)){
                break;
            }
            idx++; minNum++;
        }
        return minNum;
    }
}
