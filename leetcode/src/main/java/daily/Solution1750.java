package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/12/28 15:45
 * @author: qyl
 */
public class Solution1750 {
    public int minimumLength(String s) {
        int res = s.length (), n = res;
        int l = 0, r = n - 1;
        while (l < r) {
            if (s.charAt (l) == s.charAt (r)) {
                l++;
                r--;
                while (l <= r && s.charAt (l - 1) == s.charAt (l)) {
                    l++;
                }
                while (l <= r && s.charAt (r) == s.charAt (r + 1)) {
                    r--;
                }
            } else {
                break;
            }
            res = r - l + 1;
        }
        return res;
    }

    @Test
    public void test() {
        minimumLength ("abbbbbbbbbbbbbbbbbbba");
    }
}
