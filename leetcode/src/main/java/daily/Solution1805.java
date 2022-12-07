package daily;

import java.util.HashSet;
import java.util.Set;

/**
 * @description
 * @date:2022/12/7 20:24
 * @author: qyl
 */
public class Solution1805 {
    public int numDifferentIntegers(String word) {
        String[] split = word.split ("\\D");
        if (split.length == 0) {
            return 0;
        }
        Set<String> set = new HashSet<> (1005);
        for (String s : split) {
            if (s.trim ( ).isEmpty ( )) {
                continue;
            }
            int flag = 0;
            for (int i = 0; i < s.length ( ); i++) {
                if (s.charAt (i) != '0') {
                    set.add (s.substring (i));
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                set.add ("0");
            }
        }
        return set.size ( );
    }
}
