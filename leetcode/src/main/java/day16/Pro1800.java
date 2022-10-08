package day16;

/**
 * @description
 * @date:2022/10/7 23:55
 * @author: qyl
 */
public class Pro1800 {
    public static void main(String[] args) {
        System.out.println(new Solution1800().maxAscendingSum(new int[]{10, 20, 30, 5, 10, 50}));
    }
}

class Solution1800 {
    public int maxAscendingSum(int[] nums) {
        int res = 0 ,temp = 0;
        int len = nums.length;
        temp = nums[0];
        for (int i = 1; i < len; i++) {
            if (nums[i] > nums[i-1]){
                temp += nums[i];
            }else{
                res = Math.max(res,temp);
                temp = nums[i];
            }
        }
        return Math.max(res,temp);
    }
}
