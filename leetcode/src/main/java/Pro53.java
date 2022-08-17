package leetcode;

/**
 * projectName:  fushi
 * packageName: leetcode
 * date: 2022-03-28 18:37
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Pro53 {
    public static void main(String[] args) {
        new Solution53().maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4});
    }
}

class Solution53 {
    public int maxSubArray(int[] nums) {
        int res = Integer.MIN_VALUE, temp = 0;
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            if (nums[i] + temp >= nums[i]) {
                temp += nums[i];
            } else {
                temp = nums[i];
            }
            res = Math.max(res, temp);
        }
        return res;
    }
}
