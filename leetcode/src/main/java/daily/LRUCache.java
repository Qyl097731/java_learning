package daily;

import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @date:2023/1/20 18:22
 * @author: qyl
 */
public class LRUCache {

    Map<Integer, DLinkNode> cache = new HashMap<> ();
    int size;
    int capacity;
    DLinkNode head, tail;

    public LRUCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        head = new DLinkNode ();
        tail = new DLinkNode ();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        DLinkNode node = cache.get (key);
        if (node == null) {
            return -1;
        }
        moveToHead (node);
        return node.value;
    }

    public void put(int key, int value) {
        DLinkNode node = cache.get (key);
        if (node != null) {
            node.value = value;
            moveToHead (node);
        } else {
            node = new DLinkNode (key, value);
            cache.put (key, node);
            addToHead (node);
            size++;

            if (size > capacity) {
                DLinkNode tail = removeTail ();
                cache.remove (tail.key);
                size--;
            }
        }
    }

    private DLinkNode removeTail() {
        DLinkNode node = tail.prev;
        removeNode (node);
        return node;
    }

    private void moveToHead(DLinkNode node) {
        removeNode (node);
        addToHead (node);
    }

    private void addToHead(DLinkNode node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(DLinkNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private class DLinkNode {
        DLinkNode prev, next;
        int key, value;

        public DLinkNode() {
        }

        public DLinkNode(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
}
