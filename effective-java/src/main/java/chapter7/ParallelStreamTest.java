package chapter7;

import java.math.BigInteger;
import java.util.stream.LongStream;

/**
 * @description
 * @date:2022/9/5 14:59
 * @author: qyl
 */
public class ParallelStreamTest {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        pi((long) 1e8);
        long end = System.currentTimeMillis();
        System.out.println("pi without parallel costs " + (end - start) / 1000 + "s");
        start = System.currentTimeMillis();
        ppi((long) 1e8);
        end = System.currentTimeMillis();
        System.out.println("pi without parallel costs " + (end - start) / 1000 + "s");

    }

    static long pi(long n) {
        return LongStream.rangeClosed(2, n)
                .mapToObj(BigInteger::valueOf)
                .filter(i -> i.isProbablePrime(50))
                .count();
    }

    static long ppi(long n) {
        return LongStream.rangeClosed(2, n)
                .parallel()
                .mapToObj(BigInteger::valueOf)
                .filter(i -> i.isProbablePrime(50))
                .count();
    }
}
