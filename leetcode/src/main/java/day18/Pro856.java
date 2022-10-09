package day18;

import java.util.Stack;

/**
 * @description
 * @date:2022/10/9 0:01
 * @author: qyl
 */
public class Pro856 {
    public static void main(String[] args) {
        System.out.println(new Solution856().scoreOfParentheses("(())"));
    }
}

class Solution856 {
    public int scoreOfParentheses(String s) {
        char[] chars = s.toCharArray();
        Stack<Integer> st = new Stack<>();
        st.push(0);
        for (char c : chars) {
            if (c == '(') {
                st.push(0);
            } else {
                int v = st.pop();
                int top = st.pop() + Math.max(2 * v, 1);
                st.push(top);
            }
        }
        return st.peek();
    }
}
