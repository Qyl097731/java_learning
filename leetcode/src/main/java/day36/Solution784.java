package day36;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @date:2022/10/30 15:27
 * @author: qyl
 */
public class Solution784 {
    List<String> res = new ArrayList<>();

    public List<String> letterCasePermutation(String s) {
        dfs(s.toCharArray(), 0, s.length());
        return res;
    }

    private void dfs(char[] s, int cur, int len) {
        while (cur < len && Character.isDigit(s[cur])) {
            cur++;
        }
        if (cur == len) {
            res.add(new String(s));
            return;
        }
        s[cur] ^= 32;
        dfs(s, cur + 1, len );
        s[cur] ^= 32;
        dfs(s, cur + 1, len);
    }

    @Test
    public void test() {
        System.out.println (letterCasePermutation ("Solution2333"));
    }
}
