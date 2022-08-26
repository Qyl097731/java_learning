package chapter5;

import java.util.Collection;
import java.util.Objects;

/**
 * @author qyl
 * @program GenericMethod2.java
 * @Description 递归类型限制的泛型方法
 * @createTime 2022-08-26 16:13
 */
public class GenericMethod2 {
    public static <E extends Comparable<E>> E max(Collection<E> c){
        if (c.isEmpty()){
            throw new IllegalArgumentException();
        }
        E result = null;
        for (E e: c){
            if (result == null || e.compareTo(result) > 0){
                result = Objects.requireNonNull(e);
            }
        }
        return result;
    }
}
