package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2023/1/7 22:47
 * @author: qyl
 */
public class C {
    public int xorBeauty(int[] nums) {
//        ((nums[i] | nums[j]) & nums[k])
        int[] cnt = new int[30];
        int res = 0;
        for (int num : nums) {
            res ^= num;
        }
        return res;
    }

    @Test
    public void test() {
        System.out.println (xorBeauty (new int[]{15, 45, 20, 2, 34, 35, 5, 44, 32, 30}));
    }
}
