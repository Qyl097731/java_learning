package daily;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @description
 * @date:2023/1/5 19:06
 * @author: qyl
 */
public class NumberContainers {
    Map<Integer, PriorityQueue<Integer>> map;
    Map<Integer, Integer> pos;

    public NumberContainers() {
        map = new HashMap<> ();
        pos = new HashMap<> ();
    }

    public void change(int index, int number) {
        Integer old = pos.get (index);
        // 更新替换的数字
        if (old != null) {
            map.get (old).remove (index);
        }
        map.computeIfAbsent (number, k -> new PriorityQueue<> ()).add (index);

        pos.put (index, number);
    }

    public int find(int number) {
        PriorityQueue<Integer> queue = map.get (number);
        return queue == null || queue.isEmpty () ? -1 : queue.peek ();
    }
}
