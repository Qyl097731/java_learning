package chapter7;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @description 自定义函数接口
 * @date:2022/9/3 23:50
 * @author: qyl
 */
@FunctionalInterface
interface EldestEntryRemovalFunction<K, V> {
    boolean remove(Map<K, V> map, Map.Entry<K, V> eldest);
}

class CustomerEldestEntryRemoval<K, V> implements EldestEntryRemovalFunction<K, V> {
    @Override
    public boolean remove(Map<K, V> map, Map.Entry<K, V> eldest) {
        return map.remove(eldest.getKey()) == null ;
    }
}

public class FunctionInterfaceTest {
    public static void main(String[] args) {
        EldestEntryRemovalFunction<Integer, Integer> function = new CustomerEldestEntryRemoval<Integer, Integer>();
        Map<Integer, Integer> map = new HashMap<Integer, Integer>(1) {{
            put(1, 2);
        }};
        Set<Map.Entry<Integer, Integer>> entries = map.entrySet();
        Iterator<Map.Entry<Integer, Integer>> iterator = entries.iterator();
        System.out.println(map);
        System.out.println(function.remove(map, iterator.next()));
        System.out.println(map);
    }
}
