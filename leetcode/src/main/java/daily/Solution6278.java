package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2023/1/1 16:55
 * @author: qyl
 */
public class Solution6278 {
    public int countDigits(int num) {
        int res = 0;
        int num1 = num;
        while (num > 0) {
            if (num1 % (num % 10) == 0) {
                res++;
            }
            num /= 10;
        }
        return res;
    }

    @Test
    public void test() {
        countDigits (1248);
    }
}
