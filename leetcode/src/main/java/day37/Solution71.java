package day37;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @description
 * @date:2022/10/31 15:38
 * @author: qyl
 */
public class Solution71 {
    public String simplifyPath(String path) {
        Deque<String> stack = new ArrayDeque<>();
        StringBuilder ans = new StringBuilder();
        String[] strings = path.split("/");
        for (String string : strings) {
            if (".".equals(string) || "".equals(string)) {
                continue;
            } else if ("..".equals(string)) {
                if (!stack.isEmpty()) {
                    stack.pollLast();
                }
            } else {
                stack.offerLast(string);
            }
        }
        if (stack.isEmpty()){
            ans.append("/");
        }else {
            while (!stack.isEmpty()) {
                ans.append("/");
                ans.append(stack.pollFirst());
            }
        }
        return ans.toString();
    }

    @Test
    public void test() {
        System.out.println(simplifyPath("/a/../../b/../c//.//"));
    }
}
