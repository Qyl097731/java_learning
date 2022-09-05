package chapter7;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @description
 * @date:2022/9/5 13:53
 * @author: qyl
 */
public class StreamIterator {

    public static <E> Iterable<E> iteratorOf(Stream<E> stream){
        return stream::iterator;
    }

    public static <E> Stream<E> streamOf(Iterable<E> iterable){
        return StreamSupport.stream(iterable.spliterator(),false);
    }
}
