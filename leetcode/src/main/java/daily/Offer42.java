package daily;

/**
 * @description
 * @date:2022/12/27 20:44
 * @author: qyl
 */
public class Offer42 {
    public int maxSubArray(int[] nums) {
        int n = nums.length;
        int res = nums[0], sum = 0;
        for (int i = 0; i < n; i++) {
            res = Math.max (nums[i], res);
            if (sum + nums[i] < 0) {
                sum = 0;
            } else {
                sum += nums[i];
                res = Math.max (res, sum);
            }
        }
        return res;
    }
}
