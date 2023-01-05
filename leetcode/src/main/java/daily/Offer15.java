package daily;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2023/1/5 15:14
 * @author: qyl
 */
public class Offer15 {
    public int hammingWeight(int n) {
//        return solve1(n);
        return solve2 (n);
    }

    private int solve2(int n) {
        int res = 0;
        while (n != 0) {
            n &= (n - 1);
            res++;
        }
        return res;
    }

    private int solve1(int n) {
        int res = 0;
        for (int i = 0; i <= 31; i++) {
            if (((n >> i) & 1) != 0) {
                res++;
            }
        }
        return res;
    }

    @Test
    public void test() {
        Assertions.assertEquals (hammingWeight (11), 3);
        Assertions.assertEquals (hammingWeight (-3), 31);
    }

}
