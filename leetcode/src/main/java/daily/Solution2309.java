package daily;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @date:2022/12/12 10:49
 * @author: qyl
 */
public class Solution2309 {
    public String greatestLetter(String s) {
        List<Character> list = new ArrayList<> ( );
        String res = "";
        for (int i = 0; i < 26; i++) {
            if (s.contains (String.valueOf ((char) ('a' + i))) && s.contains (String.valueOf ((char) ('A' + i)))) {
                res = String.valueOf ((char) ('A' + i));
            }
        }
        return res;
    }
}
