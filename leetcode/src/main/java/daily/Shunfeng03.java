package daily;

/**
 * @description
 * @date:2022/12/13 10:48
 * @author: qyl
 */
public class Shunfeng03 {
    public int findMaxCI(int[] nums) {
        int res = 1;
        int n = nums.length;
        for (int i = 0; i < n; ) {
            int j = i + 1;
            while (j < n && nums[j] > nums[j - 1]) {
                j++;
            }
            if (j < n) {
                res = Math.max (j - i - 1, res);
            } else {
                res = Math.max (j - i, res);
            }
            i = j;
        }
        return res;
    }
}
