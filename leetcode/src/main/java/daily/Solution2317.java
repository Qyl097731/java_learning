package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/12/14 11:14
 * @author: qyl
 */
public class Solution2317 {
    public int maximumXOR(int[] nums) {
        int[] cnt = new int[30];
        for (int num : nums) {
            for (int i = 0; i < 30; i++) {
                if ((num & (1 << i)) != 0) {
                    cnt[i] = 1;
                }
            }
        }
        int res = 0;
        for (int i = 0; i < cnt.length; i++) {
            if (cnt[i] != 0) {
                res |= (1 << i);
            }
        }
        return res;
    }

    @Test
    public void test() {
        maximumXOR (new int[]{3, 2, 4, 6});
    }
}
