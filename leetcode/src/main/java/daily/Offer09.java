package daily;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @description
 * @date:2022/12/12 11:56
 * @author: qyl
 */
public class Offer09 {
}

class CQueue {

    Deque<Integer> stack[];
    int in = 0, out = 1;

    public CQueue() {
        stack = new Deque[]{new ArrayDeque<Integer> ( ), new ArrayDeque<Integer> ( )};
    }

    public void appendTail(int value) {
        stack[in].add (value);
    }

    public int deleteHead() {
        if (stack[out].isEmpty ( )) {
            if (stack[in].isEmpty ( )) {
                return -1;
            }
            reverse ( );
        }
        return stack[out].pop ( );
    }

    private void reverse() {
        while (!stack[in].isEmpty ( )) {
            stack[out].add (stack[in].pop ( ));
        }
    }
}
