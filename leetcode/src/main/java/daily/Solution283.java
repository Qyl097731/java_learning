package daily;

/**
 * @description
 * @date:2023/1/20 20:47
 * @author: qyl
 */
public class Solution283 {
    public void moveZeroes(int[] nums) {
        int j = 0, n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] != 0) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j++] = temp;
            }
        }
    }
}
