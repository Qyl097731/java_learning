package java8.chapter06.demo01;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * @description
 * @date:2022/10/27 23:31
 * @author: qyl
 */
public class Prime {
    /**
     * 返回小等于被除数平方根的指数集合
     */
    public static <A> List<A> takeWhile(List<A> list, Predicate<A> predicate) {
        int i = 0;
        for (A item : list) {
            if (!predicate.test(item)) {
                return list.subList(0, i);
            }
            i++;
        }
        return list;
    }

    /**
     * 通过不大于平方根的指数集合进行测试
     */
    public static boolean isPrime(List<Integer> prime, int candidate) {
        int candidateRoot = (int) Math.sqrt(candidate);
        return takeWhile(prime, i -> i <= candidateRoot)
                .stream().noneMatch(i -> candidate % i == 0);
    }
}
