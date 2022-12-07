package daily;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @date:2022/12/7 21:41
 * @author: qyl
 */
public class Solution2284 {
    Map<String, Integer> map = new HashMap<> ( );

    int cnt = -1;
    String name;

    public String largestWordCount(String[] messages, String[] senders) {
        int n = messages.length;
        for (int i = 0; i < n; i++) {
            Integer cnt = map.getOrDefault (senders[i], 0) + messages[i].split (" ").length;
            map.put (senders[i], cnt);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet ( )) {
            if (entry.getValue ( ) > cnt) {
                cnt = entry.getValue ( );
                name = entry.getKey ( );
            } else if (entry.getValue ( ) == cnt) {
                if (entry.getKey ( ).compareTo (name) > 0) {
                    name = entry.getKey ( );
                }
            }
        }
        return name;
    }

    @Test
    public void test() {
        String[] m = {"Hello userTwooo", "Hi userThree", "Wonderful day Alice", "Nice day userThree"}, s =
                {"Alice", "u", "userThree", "Blice"};

        System.out.println (largestWordCount (m, s));
    }
}
