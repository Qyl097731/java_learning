package daily;

import java.util.ArrayDeque;

/**
 * @description
 * @date:2022/12/18 0:23
 * @author: qyl
 */
public class Offer30 {
}

class MinStack {
    ArrayDeque<Integer> stack1, stack2;

    /**
     * initialize your data structure here.
     */
    public MinStack() {
        stack1 = new ArrayDeque<> ( );
        stack2 = new ArrayDeque<> ( );
        stack2.push (Integer.MAX_VALUE);
    }

    public void push(int x) {
        stack1.offerLast (x);
        stack2.offerLast (Math.min (x, stack2.peekLast ( )));
    }

    public void pop() {
        stack2.pollLast ( );
        stack1.pollLast ( );
    }

    public int top() {
        return stack1.peekLast ( );
    }

    public int min() {
        return stack2.peekLast ( );
    }
}
