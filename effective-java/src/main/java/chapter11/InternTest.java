package chapter11;

import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @description
 * @date:2022/9/7 17:32
 * @author: qyl
 */
public class InternTest {
    private static final int MAX_COUNT = 1000 * 10000;
    private static final String[] arr = new String[MAX_COUNT];
    private static final ConcurrentMap<String, String> map = new ConcurrentHashMap<>();
    Integer[] data = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    public static String intern(String s) {
        String result = map.get(s);
        if (result == null) {
            result = map.putIfAbsent(s, s);
        }
        if (result == null) {
            result = s;
        }
        return result;
    }

    public static String intern2(String s) {
        return map.getOrDefault(s, map.put(s, s));
    }

    @Test
    public void test1() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX_COUNT; i++) {
            arr[i] = new String(String.valueOf(data[i % data.length])).intern();
        }
        long end = System.currentTimeMillis();
        System.out.println("花费的时间为：" + (end - start));
    }

    @Test
    public void test2() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX_COUNT; i++) {
            arr[i] =intern(String.valueOf(data[i % data.length]));
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);

    }

    @Test
    public void test3() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX_COUNT; i++) {
            arr[i] =intern2(String.valueOf(data[i % data.length]));
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

}
