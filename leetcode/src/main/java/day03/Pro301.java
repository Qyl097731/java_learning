package day03;

import java.util.*;

/**
 * @author qyl
 * @program Pro301.java
 * @Description 删除无效的括号 搜索
 * @createTime 2022-08-26 11:26
 */
public class Pro301 {
    public static void main(String[] args) {
        System.out.println(new Solution301().removeInvalidParentheses("()())()"));
    }
}

class Solution301 {
    Set<String> res = new HashSet<>();
    int lbrace = 0, rbrace = 0;

    public List<String> removeInvalidParentheses(String s) {
        int len = s.length();
        char c;
        for (int i = 0; i < len; i++) {
            c = s.charAt(i);
            if (c == '(') {
                lbrace++;
            } else if (c == ')') {
                if (lbrace > 0) {
                    lbrace--;
                } else {
                    rbrace++;
                }
            }
        }

        dfs(s, 0, len);

        return new ArrayList<>(res);
    }

    private void dfs(String s, int start, int len) {
        if (lbrace == 0 && rbrace == 0 && check(s)) {
            res.add(s);
            return;
        }
        if (lbrace +  rbrace> len - start ){
            return;
        }
        for (;start < len;start++){
            char c = s.charAt(start);
            if (lbrace > 0 && c == '('){
                lbrace--;
                dfs(s.substring(0,start)+s.substring(start+1),start,len-1);
                lbrace++;
            }
            if (rbrace > 0 && c == ')'){
                rbrace--;
                dfs(s.substring(0,start)+s.substring(start+1),start,len-1);
                rbrace++;
            }
        }
    }

    private boolean check(String s) {
        int len = s.length();
        int lb = 0 , rb = 0;
        char c;
        for (int i = 0; i < len; i++) {
            c = s.charAt(i);
            if (c == '(') {
                lb++;
            } else if (c == ')') {
                if (lb > 0) {
                    lb--;
                } else {
                    rb++;
                }
            }
        }
        return rb == 0 && lb == 0;
    }
}
