package chapter10;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * projectName:  jvm
 * packageName: chapter10 @date: 2022-07-30 22:42
 *
 * @author 邱依良
 */
public class MaxDirectMemorySizeTest {
    private static final long _1MB = 1024 * 1024;

    public static void main(String[] args) throws IllegalAccessException {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe)unsafeField.get(null);
        while (true) {
            unsafe.allocateMemory(_1MB);
        }
    }
}
