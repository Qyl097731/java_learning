package java8.day02.stream03;

import java8.day01.selectEmployee.Employee;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * projectName:  java_learing
 * packageName: java8.day02
 * date: 2022-04-03 19:30
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Stream03 {
    List<Employee> list = new ArrayList<>(Arrays.asList(
            new Employee(31, 301, Employee.Status.FREE),
            new Employee(32, 302, Employee.Status.BUSY),
            new Employee(59, 306, Employee.Status.FREE),
            new Employee(99, 301, Employee.Status.VOCATION),
            new Employee(99, 301, Employee.Status.FREE),
            new Employee(33, 303, Employee.Status.VOCATION),
            null
    ));

    /*
        sorted 自然排序Comparable
        sorted(Comparator com) 按照自己定义的规则排序
     */
    @Test
    public void test01() {
        list.stream()
                .sorted((e1, e2) -> {
                    if (e1.getAge() == e2.getAge()) {
                        return e1.getSalary() - e2.getSalary();
                    } else {
                        return e1.getAge() - e2.getAge();
                    }
                })
                .forEach(System.out::println);
    }

    /*
     * 查找与匹配
     * allMatch 检查是否匹配所有元素
     * anyMatch 检查是否存在一个匹配元素
     * noneMatch 检查是否所有元素未匹配
     * findFirst 查找第一个元素
     * findAny 返回流中任意一个元素
     * count 返回流中元素的总个数
     * max 返回流中最大值
     * min 返回流中最小值
     */
    @Test
    public void test02() {
        boolean b1 = list.stream()
                .allMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b1);

        boolean b2 = list.stream()
                .anyMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b2);

        boolean b3 = list.stream()
                .noneMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b3);

    }

    @Test
    public void test03() {
        System.out.println("findFirst");
        Optional<Employee> op = list.stream()
                .filter(Objects::nonNull)
                .findFirst();
        System.out.println(op.get());
        System.out.println("------------");


        System.out.println("max");
        System.out.println(list.stream().filter(Objects::nonNull).max((e1, e2) ->
                e1.getSalary() - e2.getSalary())
                .get());
        System.out.println("------------");


        System.out.println("findAny");
        System.out.println(list.stream().filter(employee -> employee.getSalary() > 300).findAny().get());
        System.out.println("------------");

    }
}
