package daily;

import java.util.HashSet;
import java.util.Set;

/**
 * description
 * date:2022/12/3 21:42
 * author: qyl
 */
public class Solution1796 {
    public int secondHighest(String s) {
        Set<Integer> temp = new HashSet<> ( );
        for (char c : s.toCharArray ( )) {
            if (Character.isDigit (c)) {
                temp.add (c - '0');
            }
        }
        Integer[] array = temp.toArray (new Integer[0]);
        return temp.size ( ) >= 2 ? array[array.length - 2] : -1;
    }
}
