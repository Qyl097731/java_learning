package daily;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @date:2022/12/14 10:41
 * @author: qyl
 */
public class Solution2315 {
    public int countAsterisks(String s) {
        int n = s.length ( );
        int res = 0;
        List<Integer> pos = new ArrayList<> ( );
        for (int i = 0; i < n; i++) {
            if (s.charAt (i) == '|') {
                pos.add (i);
            }
            if (s.charAt (i) == '*') {
                res++;
            }
        }
        for (int i = 0; i + 1 < pos.size ( ); i += 2) {
            for (int j = pos.get (i) + 1; j < pos.get (i + 1); j++) {
                if (s.charAt (j) == '*') {
                    res--;
                }
            }
        }
        return res;
    }

    @Test
    public void test() {
        countAsterisks ("|**|*|*|**||***||");
    }
}
