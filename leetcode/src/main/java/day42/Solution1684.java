package day42;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @date:2022/11/11 11:16
 * @author: qyl
 */
public class Solution1684 {
    public int countConsistentStrings(String allowed, String[] words) {
        return (int) Arrays.stream(words).filter(w->{
            boolean flag = true;
            for (char c : w.toCharArray()) {
                if (allowed.indexOf(c) == -1){
                    flag = false;
                    break;
                }
            }
            return flag;
        }).count();
    }

    @Test
    public void test(){
        assertEquals(countConsistentStrings("ab", new String[]{"ad", "bd", "aaab", "baa", "badab"}),2);

        assertEquals(countConsistentStrings("abc", new String[]{"a","b","c","ab","ac","bc","abc"}),7);
        assertEquals(countConsistentStrings("cad", new String[]{"cc","acd","b","ba","bac","bad","ac","d"}),4);

    }
}
