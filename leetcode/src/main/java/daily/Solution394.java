package daily;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

/**
 * @description
 * @date:2023/1/18 14:56
 * @author: qyl
 */
public class Solution394 {
    int ptr = 0;
    String s;

    public String decodeString(String s) {
//        return solve1(s);
        return solve2 (s);
    }

    private String solve2(String s) {
        this.s = s;
        return getString ();
    }

    private String getString() {
        if (ptr == s.length () || s.charAt (ptr) == ']') {
            return "";
        }
        StringBuilder res = new StringBuilder ();
        if (Character.isDigit (s.charAt (ptr))) {
            int num = Integer.parseInt (getDigit (s));
            ptr++;
            String sub = getString ();
            ptr++;
            while (num-- > 0) {
                res.append (sub);
            }
        } else if (Character.isLetter (s.charAt (ptr))) {
            res = new StringBuilder (String.valueOf (s.charAt (ptr++)));
        }
        return res.append (getString ()).toString ();
    }

    private String solve1(String s) {
        int n = s.length ();
        LinkedList<String> stack = new LinkedList<> ();
        while (ptr < n) {
            if (Character.isDigit (s.charAt (ptr))) {
                String digit = getDigit (s);
                stack.addLast (digit);
            } else if (s.charAt (ptr) != ']') {
                stack.addLast (String.valueOf (s.charAt (ptr++)));
            } else {
                LinkedList<String> temp = new LinkedList<> ();
                // [abc] 取出abc
                while (!"[".equals (stack.peekLast ())) {
                    temp.offerFirst (stack.pollLast ());
                }
                stack.pollLast ();

                // 拼接字符串
                String rev = getString (temp);
                int num = Integer.parseInt (stack.pollLast ());
                StringBuilder tempV = new StringBuilder ();
                while (num-- > 0) {
                    tempV.append (rev);
                }
                stack.addLast (tempV.toString ());
                ptr++;
            }
        }
        return getString (stack);
    }

    private String getString(LinkedList<String> temp) {
        StringBuilder res = new StringBuilder ();
        for (String s : temp) {
            res.append (s);
        }
        return res.toString ();
    }

    public String getDigit(String s) {
        StringBuilder res = new StringBuilder ();
        while (Character.isDigit (s.charAt (ptr))) {
            res.append (s.charAt (ptr++));
        }
        return res.toString ();
    }

    @Test
    public void test() {
        decodeString ("2[bc]");
    }
}
