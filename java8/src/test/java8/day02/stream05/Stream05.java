package java8.day02.stream05;

import java8.day01.selectEmployee.Employee;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * projectName:  java_learing
 * packageName: java8.day02.stream05
 * date: 2022-04-03 22:18
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Stream05 {
    List<Employee> list = new ArrayList<>(Arrays.asList(
            new Employee(31, 301, Employee.Status.FREE),
            new Employee(32, 302, Employee.Status.BUSY),
            new Employee(59, 306, Employee.Status.FREE),
            new Employee(99, 301, Employee.Status.VOCATION),
            new Employee(99, 301, Employee.Status.FREE),
            new Employee(33, 303, Employee.Status.VOCATION)
            ));
    /**
     * 将数组中的值平方后返回
     */
    @Test
    public void test01() {
        Integer[] nums = new Integer[]{1, 2, 3, 4, 5};
        Arrays.stream(nums)
                .map(num -> num * num)
                .collect(Collectors.toList())
                .forEach(System.out::println);

    }

    /**
     * 通过map 和 reduce 统计流中有多少个Employee
     */
    @Test
    public void test02(){
        Integer cnt = list.stream()
                .map(employee -> 1)
                .reduce(0,Integer::sum);
        System.out.println(cnt);
    }
}
