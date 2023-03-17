package daily;

import org.junit.jupiter.api.Test;

import java.util.Stack;

/**
 * @description
 * @date 2023/3/17 22:52
 * @author: qyl
 */
public class Solution31 {
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Stack<Integer> stack = new Stack<> ();
        int index = 0;
        for (int num : pushed) {
            stack.push (num);
            while (!stack.isEmpty () && popped[index] == stack.peek ()) {
                index++;
                stack.pop ();
            }
        }
        while (index < popped.length && popped[index] == stack.peek ()) {
            index++;
            stack.pop ();
        }
        return index == popped.length;
    }

    @Test
    public void test() {
        validateStackSequences (new int[]{2, 1, 0}, new int[]{1, 2, 0});
    }
}
