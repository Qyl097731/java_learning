package day48;

/**
 * @description
 * @date:2022/11/20 10:28
 * @author: qyl
 */
public class Solution6241 {
    public int unequalTriplets(int[] nums) {
        int n = nums.length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (nums[i] != nums[j] && nums[i] != nums[k] && nums[j] != nums[k])
                        res++;
                }
            }
        }
        return res;
    }
}
