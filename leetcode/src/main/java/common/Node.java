package common;

/**
 * @description
 * @date:2022/12/19 16:34
 * @author: qyl
 */
public class Node {
    public int val;
    public Node next;
    public Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
