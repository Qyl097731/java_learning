package daily;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/12/9 17:33
 * @author: qyl
 */
public class Solution1780 {
    public boolean checkPowersOfThree(int n) {
        int cur = (int) Math.pow (3, 15);
        while (cur > 0) {
            if (n >= cur) {
                n -= cur;
            }
            cur /= 3;
        }
        return n == 0;
    }

    @Test
    public void test() {
        Assertions.assertTrue (checkPowersOfThree (12));
    }
}
