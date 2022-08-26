package chapter5;

import java.util.Arrays;
import java.util.List;

/**
 * @author qyl
 * @program GenericLimitedWildcardMethod.java
 * @Description 辅助方法，捕捉通配符类型
 * @createTime 2022-08-26 17:24
 */
public class GenericLimitedWildcardMethod {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3);
        swap(list,1,2);
        System.out.println(list);
    }

    public static void swap(List<?> list, int i, int j) {
        swapHelper(list, i, j);
    }

    public static <E> void swapHelper(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }
}
