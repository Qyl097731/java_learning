package daily;

/**
 * @description
 * @date:2023/1/8 10:30
 * @author: qyl
 */
public class Solution6282 {
    public int maximumCount(int[] nums) {
        int cnt1 = 0, cnt2 = 0;
        for (int num : nums) {
            if (num < 0) {
                cnt1++;
            } else if (num > 0) {
                cnt2++;
            }
        }
        return Math.max (cnt1
                , cnt2);
    }
}
