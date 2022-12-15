package daily;

import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @date:2022/12/15 23:20
 * @author: qyl
 */
public class Solution2325 {
    public String decodeMessage(String key, String message) {
        int n = key.length ( );
        int cur = 0;
        Map<Character, Character> map = new HashMap<> ( );
        map.put (' ', ' ');
        for (int i = 0; i < n; i++) {
            if (map.get (key.charAt (i)) == null) {
                map.put (key.charAt (i), (char) (cur + 'a'));
                cur++;
            }
        }
        StringBuilder res = new StringBuilder ( );
        int m = message.length ( );
        for (int i = 0; i < m; i++) {
            res.append (map.get (message.charAt (i)));
        }
        return res.toString ( );
    }
}
