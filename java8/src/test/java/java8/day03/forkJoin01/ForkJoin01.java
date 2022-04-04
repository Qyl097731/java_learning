package java8.day03.forkJoin01;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * projectName:  java_learing
 * packageName: java8.day03.ForkJoin01
 * date: 2022-04-04 15:48
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class ForkJoin01 {

    /**
     * Fork Join 框架
     */
    @Test
    public void test01() {
        Instant start = Instant.now();

        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinCalculate(0, 1000000000L);
        Long sum = pool.invoke(task);
        System.out.println(sum);

        Instant end = Instant.now();
        System.out.println("耗费时间 ： " + Duration.between(start, end).toMillis()); //304ms
    }
    /**
     *
     * 普通for循环
     *
     */
    @Test
    public void test02() {
        Instant start = Instant.now();

        long sum = 0;
        for (long i = 0; i <= 1000000000L; i++) {
            sum += i;
        }
        System.out.println(sum);

        Instant end = Instant.now();
        System.out.println("耗费时间 ： " + Duration.between(start, end).toMillis());   //393ms

    }

    /**
     *
     * Java8 并行流
     *
     */
    @Test
    public void test03(){
        Instant start = Instant.now();

        long sum = LongStream.rangeClosed(0, 1000000000L)
                .parallel()
                .sum();
        System.out.println(sum);

        Instant end = Instant.now();
        System.out.println("耗费时间 ： " + Duration.between(start, end).toMillis());   //393ms

    }
}
