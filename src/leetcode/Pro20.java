package leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * projectName:  fushi
 * packageName: leetcode
 * date: 2022-03-27 21:47
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Pro20 {
}
class Solution20 {
    public boolean isValid(String s) {

        Stack<Character>stack = new Stack<>();
        int len = s.length();
        for(int i = 0 ; i < len ; i++){
            char c = s.charAt(i),top;
            if(stack.isEmpty()){
                stack.push(c);
            }else{
                top = stack.peek();
                if(top == '(' && c == ')'){
                    stack.pop();
                }else if(top == '[' && c == ']'){
                    stack.pop();
                }else if(top == '{' && c == '}'){
                    stack.pop();
                }else {
                    stack.push(c);
                }
            }
        }
        if(stack.isEmpty()){
            return true;
        }
        return false;
    }
}
