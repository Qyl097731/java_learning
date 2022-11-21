package day49;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/11/21 16:24
 * @author: qyl
 */
public class Solution2259 {
    public String removeDigit(String number, char digit) {
        int n = number.length ( );
        String res = null;
        for (int i = -1; ; ) {
            i = number.indexOf (digit, i + 1);
            if (i == -1) {
                break;
            }
            String temp = number.substring (0, i) + number.substring (i + 1, n);
            if (res == null || res.compareTo (temp) < 0) {
                res = temp;
            }
        }
        return res;
    }

    @Test
    public void test() {
        System.out.println (removeDigit ("123", '3'));
    }
}
