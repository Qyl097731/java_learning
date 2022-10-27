package day34;

/**
 * @description
 * @date:2022/10/28 0:17
 * @author: qyl
 */
class Solution1822 {
    public int arraySign(int[] nums) {
        int flag = 1;
        for (int num : nums) {
            if (num == 0) {
                return 0;
            }
            flag *= num > 1 ? 1 : -1;
        }
        return flag;
    }
}
