package daily;

import common.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @date:2022/12/19 16:34
 * @author: qyl
 */
public class offer35 {
    Map<Node, Node> cachedMap = new HashMap<> ( );

    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        if (!cachedMap.containsKey (head)) {
            Node node = new Node (head.val);
            cachedMap.put (head, node);
            node.next = copyRandomList (head.next);
            node.random = copyRandomList (head.random);
        }
        return cachedMap.get (head);
    }
}
