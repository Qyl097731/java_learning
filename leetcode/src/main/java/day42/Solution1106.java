package day42;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @description
 * @date:2022/11/11 10:34
 * @author: qyl
 */
public class Solution1106 {
    public boolean parseBoolExpr(String expression) {
        Deque<Character> exp = new ArrayDeque<>(), op = new ArrayDeque<>();
        int f = 0, t = 0;
        for (char c : expression.toCharArray()) {
            if (c == '(' || Character.isAlphabetic(c)) {
                exp.push(c);
            } else if (c == ')') {
                f = 0;
                t = 0;
                while (exp.peek() != '(') {
                    if (exp.peek() == 't') {
                        t++;
                    } else {
                        f++;
                    }
                    exp.pop();
                }
                exp.pop();
                if (!op.isEmpty()) {
                    Character opc = op.pop();
                    if (opc == '!') {
                        exp.push(t > 0 ? 'f' : 't');
                    } else if (opc == '&') {
                        exp.push(f > 0 ? 'f' : 't');
                    } else if (opc == '|') {
                        exp.push(t > 0 ? 't' : 'f');
                    }
                }
            } else if (c == ',') {
                continue;
            } else {
                op.push(c);
            }
        }
        return exp.pop() == 't';
    }

    @Test
    public void test(){
        assertTrue(parseBoolExpr("!(f)"));
        assertTrue(parseBoolExpr("|(f,t)"));
        assertFalse(parseBoolExpr("&(t,f)"));
        assertFalse(parseBoolExpr("|(&(t,f,t),!(t))"));

    }
}
