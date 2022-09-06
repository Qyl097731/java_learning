package chapter9;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @description
 * @date:2022/9/6 20:42
 * @author: qyl
 */
public class IntegerComparator {
    public static void main(String[] args) {
        Comparator<Integer> naturalOrder = (i, j) -> i > j ? 1 : (i == j ? 0 : -1);
        List<Integer> list = new ArrayList<>(List.of(2,4,1,4));

        Collections.sort(list,naturalOrder);
        System.out.println(list);

        System.out.println("--------------------------");

        System.out.println(naturalOrder.compare(Integer.valueOf(142),Integer.valueOf(142)));
    }
}
