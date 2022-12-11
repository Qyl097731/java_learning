package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/12/10 22:33
 * @author: qyl
 */
public class Solution6251 {
    public int maximumValue(String[] strs) {
        int res = 0;
        int n = strs.length;
        for (int i = 0; i < n; i++) {
            String str = strs[i];
            boolean flag = true;
            for (int j = 0; j < str.length ( ); j++) {
                if (!Character.isDigit (str.charAt (j))) {
                    flag = false;
                }
            }
            if (flag) {
                res = Math.max (res, Integer.parseInt (str));
            } else {
                res = Math.max (res, str.length ( ));
            }
        }
        return res;
    }

    @Test
    public void test() {
        maximumValue (new String[]{"1", "01", "001", "0001"});
    }
}
