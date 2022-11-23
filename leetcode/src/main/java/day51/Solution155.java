package day51;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @description
 * @date:2022/11/23 11:01
 * @author: qyl
 */
public class Solution155 {
}

class MinStack {
    Deque<Integer> minStack, stack;

    public MinStack() {
        stack = new ArrayDeque<> ( );
        minStack = new ArrayDeque<> ( );
        minStack.offer (Integer.MAX_VALUE);
    }

    public void push(int val) {
        stack.offer (val);
        if (minStack.peekLast ( ) >= val) {
            minStack.offerLast (val);
        }
        minStack.offerLast (val);
    }

    public void pop() {
        int val = stack.pollLast ( );
        System.out.println (val);
        System.out.println (minStack.peekLast ( ));
        if (val == minStack.peekLast ( )) {
            minStack.pollLast ( );
        }
    }

    public int top() {
        return stack.peekLast ( );
    }

    public int getMin() {
        return minStack.peekLast ( );
    }
}

