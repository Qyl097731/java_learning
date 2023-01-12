package daily;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description
 * @date:2023/1/12 23:23
 * @author: qyl
 */
public class Solution1807 {
    public String evaluate(String s, List<List<String>> knowledge) {
        Map<String, String> map = new HashMap<> ();
        for (List<String> kv : knowledge) {
            map.put (kv.get (0), kv.get (1));
        }
        StringBuilder sb = new StringBuilder ();
        int n = s.length ();
        int l = 0, r = 0;
        while (r < n) {
            if (s.charAt (r) == '(') {
                l = r + 1;
                while (r < n && s.charAt (r) != ')') {
                    r++;
                }
                sb.append (map.getOrDefault (s.substring (l, r), "?"));
            } else {
                sb.append (s.charAt (r));
            }
            r++;
        }
        return sb.toString ();
    }
}
