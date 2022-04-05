package java8.day02.stream01;

import java8.day01.selectEmployee.Employee;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * projectName:  java_learing
 * packageName: java8.day02
 * date: 2022-04-03 18:17
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Steam01 {
    //创建stream
    @Test
    public void test1() {
        //1 通过collection系列集合提供的stream 或 parallelStream
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream();

        //2 通过Arrays中的静态方法stream 获取数组流
        Employee[] emps = new Employee[10];
        Stream<Employee> stream1 = Arrays.stream(emps);

        //3 通过Stream类中的静态方法of
        Stream<String> stream2 = Stream.of("aa", "bb", "cc");

        //4 创建无限流

        //迭代
        Stream<Integer> iterate = Stream.iterate(0, x -> x + 2).limit(10);
        iterate.forEach(System.out::println);

        //生成
        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);
    }
}
