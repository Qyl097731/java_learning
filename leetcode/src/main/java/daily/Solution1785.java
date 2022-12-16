package daily;

/**
 * @description
 * @date:2022/12/16 18:11
 * @author: qyl
 */
public class Solution1785 {
    public int minElements(int[] nums, int limit, int goal) {
        long sum = 0;
        for (int num : nums) {
            sum += num;
        }
        return (int) ((Math.abs (sum - goal) + limit - 1) / limit);
    }
}
