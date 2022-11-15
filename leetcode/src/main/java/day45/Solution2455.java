package day45;

/**
 * @description
 * @date:2022/11/15 0:05
 * @author: qyl
 */
public class Solution2455 {
    public int averageValue(int[] nums) {
        int res = 0, cnt = 0;
        for (int num : nums) {
            if (num % 3 == 0 && (num & 1) == 0) {
                res += num;
                cnt++;
            }
        }
        return cnt == 0 ? 0 : res / cnt;
    }
}
