package chapter13;

import java.util.concurrent.TimeUnit;

/**
 * @description 空间测试
 * @date:2022/8/5 23:49
 * @author: qyl
 */
public class StringIntern4 {
    static final int MAX_COUNT = 1000 * 10000;
    static final String[] arr = new String[MAX_COUNT];

    public static void main(String[] args) throws InterruptedException {
        Integer[] data = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8};

        long start = System.currentTimeMillis();

        for (int i = 0; i < MAX_COUNT; i++) {
//            arr[i] = new String(String.valueOf(data[i % data.length]).intern()); // 2413
            arr[i] = new String(String.valueOf(data[i % data.length])); // 3572

        }

        long end = System.currentTimeMillis();

        System.out.println(end - start);

        TimeUnit.SECONDS.sleep(100);
    }
}
