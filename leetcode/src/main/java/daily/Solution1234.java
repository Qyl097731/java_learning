package daily;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @date 2023/2/13 19:27
 * @author: qyl
 */
public class Solution1234 {
    public int balancedString(String s) {
        Map<Character, Integer> map = new HashMap<Character, Integer> () {{
            put ('Q', 0);
            put ('W', 1);
            put ('E', 2);
            put ('R', 3);
        }};
        int[] total = new int[4];
        int n = s.length ();
        int len = n / 4;
        int res = n;
        for (int i = 0; i < n; i++) {
            total[map.get (s.charAt (i))]++;
        }
        if (check (total, len)) {
            return 0;
        }
        int l = 0, r = 0;
        while (l < n) {
            while (r < n && !check (total, len)) {
                total[map.get (s.charAt (r))]--;
                r++;
            }
            if (!check (total, len)) {
                break;
            }
            res = Math.min (r - l, res);
            total[map.get (s.charAt (l++))]++;
        }
        return res;
    }

    private boolean check(int[] total, int len) {
        for (int i = 0; i < 4; i++) {
            if (total[i] > len) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void test() {
        Assertions.assertEquals (balancedString ("QQWE"), 1);
        Assertions.assertEquals (balancedString ("QQQW"), 2);
        Assertions.assertEquals (balancedString ("QQQQ"), 3);
    }

}
