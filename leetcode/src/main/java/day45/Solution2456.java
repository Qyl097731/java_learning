package day45;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @description
 * @date:2022/11/15 0:11
 * @author: qyl
 */
public class Solution2456 {
    public List<List<String>> mostPopularCreator(String[] creators, String[] ids, int[] views) {
        int n = ids.length;
        long maxV = 0;
        Map<String, Integer> w = new HashMap<>(n);
        Map<String, Long> c = new HashMap<>(n);
        for (int i = 0; i < n; i++) {
            c.put(creators[i], c.getOrDefault(creators[i], 0L) + views[i]);
            maxV = Math.max(maxV, c.get(creators[i]));
            Integer id = w.getOrDefault(creators[i], i);
            if (views[id] < views[i] || (ids[id].compareTo(ids[i]) >= 0 && views[id] == views[i])) {
                w.put(creators[i], i);
            }
        }
        List<List<String>> res = new ArrayList<>();
        for (Map.Entry<String, Long> entry : c.entrySet()) {
            if (entry.getValue() == maxV) {
                res.add(Arrays.asList(entry.getKey(), ids[w.get(entry.getKey())]));
            }
        }

        return res;
    }

    @Test
    public void test() {
        String[] creators = {"alice", "bob", "alice", "chris"}, ids = {"one", "two", "three", "four"};
        int[] views = {5, 10
                , 5, 4};

        mostPopularCreator(creators, ids, views);
    }
}
