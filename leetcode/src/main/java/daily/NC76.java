package daily;

import java.util.Stack;

/**
 * @description
 * @date 2023/3/9 21:58
 * @author: qyl
 */
public class NC76 {
    Stack<Integer> in = new Stack<Integer> ();
    Stack<Integer> out = new Stack<Integer> ();

    public void push(int node) {
        in.push (node);
    }

    public int pop() {
        if (out.isEmpty ()) {
            while (!in.isEmpty ()) {
                out.push (in.pop ());
            }
        }
        return out.pop ();
    }
}
