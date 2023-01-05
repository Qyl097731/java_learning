package daily;

/**
 * @description
 * @date:2023/1/5 18:42
 * @author: qyl
 */
public class Offer56I {
    public int[] singleNumbers(int[] nums) {
        int xorNum = 0;
        for (int num : nums) {
            xorNum ^= num;
        }
        int div = 1;
        while ((div & xorNum) == 0) {
            div <<= 1;
        }

        int a = 0, b = 0;
        for (int num : nums) {
            if ((num & div) != 0) {
                a ^= num;
            } else {
                b ^= num;
            }
        }
        return new int[]{a, b};
    }
}
