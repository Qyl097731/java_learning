package day31;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @description
 * @date:2022/10/23 12:39
 * @author: qyl
 */
public class Pro6214 {
}

class Solution {
    public boolean haveConflict(String[] event1, String[] event2) {
        String[][] s = new String[][]{event1, event2};
        Arrays.sort(s, Comparator.comparing(a -> a[0]));
        return s[0][1].compareTo(s[1][0]) >= 0;
    }
}
