package daily;

import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @date:2022/12/13 12:08
 * @author: qyl
 */
public class Solution1832 {
    public boolean checkIfPangram(String sentence) {
        int n = sentence.length ( );
        Map<Integer, Integer> map = new HashMap<> ( );
        for (int i = 0; i < n; i++) {
            map.put ((int) sentence.charAt (i), 1);
        }

        for (int i = 0; i < 26; i++) {
            if (map.getOrDefault (i + 'a', 0) == 0) {
                return false;
            }
        }
        return true;
    }
}
