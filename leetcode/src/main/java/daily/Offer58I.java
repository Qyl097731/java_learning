package daily;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @description
 * @date:2023/1/1 20:04
 * @author: qyl
 */
public class Offer58I {
    public String reverseWords(String s) {
        List<String> strings = Arrays.asList (s.trim ().split ("\\s+"));
        Collections.reverse (strings);
        return String.join (" ", strings);
    }
}
