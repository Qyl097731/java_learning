package chapter7;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @description 子列表
 * @date:2022/9/5 14:27
 * @author: qyl
 */
public class SubLists {
    public static <E> Stream<List<E>> of(List<E> list){
        return Stream.concat(Stream.of(Collections.emptyList()),prefixes(list).flatMap(SubLists::suffixed));
    }

    public static <E> Stream<List<E>> prefixes(List<E> list) {
        return IntStream.rangeClosed(1, list.size()).mapToObj(end -> list.subList(0, end));
    }

    public static <E> Stream<List<E>> suffixed(List<E> list) {
        return IntStream.range(0, list.size()).mapToObj(start -> list.subList(start, list.size()));
    }

}
