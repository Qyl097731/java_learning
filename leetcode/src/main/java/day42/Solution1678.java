package day42;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @description
 * @date:2022/11/11 11:07
 * @author: qyl
 */
public class Solution1678 {
    public String interpret(String command) {
        String[] exp = new String[]{"G", "(al)", "()"};
        String[] map = new String[]{"G", "al", "o"};
        StringBuilder res = new StringBuilder();
        int n = command.length();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 3; j++) {
                int end = i + exp[j].length();
                if (end <= n) {
                    if (command.substring(i, end).equals(exp[j])) {
                        res.append(map[j]);
                    }
                }
            }
        }
        return res.toString();
    }

    @Test
    public void test(){
        assertEquals(interpret("G()()()()(al)"),"Gooooal");
        assertEquals(interpret("G()(al)"),"Goal");
        assertEquals(interpret("(al)G(al)()()G"),"alGalooG");
    }
}
