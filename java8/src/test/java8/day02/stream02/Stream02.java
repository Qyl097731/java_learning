package java8.day02.stream02;

import java8.day01.selectEmployee.Employee;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * projectName:  java_learing
 * packageName: java8.day02.stream02
 * date: 2022-04-03 18:32
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Stream02 {
    List<Employee> list = new ArrayList<>(Arrays.asList(
            new Employee(31, 301, Employee.Status.FREE),
            new Employee(32, 302, Employee.Status.BUSY),
            new Employee(59, 306, Employee.Status.FREE),
            new Employee(99, 301, Employee.Status.VOCATION),
            new Employee(99, 301, Employee.Status.FREE),
            new Employee(33, 303, Employee.Status.VOCATION)
    ));

    //中间操作

    /*
        筛选和切片
        filter 接受lambda ，从流中排除某些元素
        limit 截断流
        skip(n) 跳过元素 返回一个扔掉前n个元素的流，若元素不足n就返回一个空流
        distinct 筛选，通过hash 和  equals 去重
    */
    @Test
    public void test01() {
        //如果没有终止操作 那么就不会又任何操作，又称为惰性求值
        list.stream()
                .filter((employee -> {
                    System.out.println("执行……");
                    return employee.getAge() < 32;
                }))
                .forEach(System.out::println);
    }

    @Test
    public void test02() {
        //limit 利用短路提升效率 如果满足个数 就不再执行
        list.stream()
                .filter((employee -> {
                    System.out.println("执行……");
                    return employee.getAge() < 40;
                }))
                .limit(2)
                .forEach(System.out::println);
    }

    @Test
    public void test03() {
        //如果没有终止操作 那么就不会又任何操作，又称为惰性求值
        list.stream()
                .filter((employee -> {
                    System.out.println("执行……");
                    return employee.getAge() > 30;
                }))
                .skip(2)
                .distinct()
                .forEach(System.out::println);
    }

    /*
        映射
        map 接受lambda 将元素转换成其他形式或提取信息。接受一个函数作为参数，该函数将每个元素映射成新元素
        flatMap 接受一个函数 将流中的每个值换成另一个流 最后链接所有流
     */
    @Test
    public void test04() {
        List<String> strings = Arrays.asList("aaa", "bbb", "ccc");
        strings.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println);

        System.out.println("---------");

        list.stream()
                .map(Employee::getAge)
                .forEach(System.out::println);

    }
    @Test
    public void test05(){
        List<String> strings = Arrays.asList("aaa", "bbb", "ccc");

        Stream<Stream<Character>> streamStream = strings.stream().map(Stream02::getCharacterStream);
        streamStream.forEach(
                sm->sm.forEach(System.out::println)
        );
        System.out.println("---------");

        Stream<Character> characterStream = strings.stream().flatMap(Stream02::getCharacterStream);
        characterStream.forEach(System.out::println);

        /*
         flatMap 和 map 和 add addAll相似原理
         */
        System.out.println("addAll--------");
        List<String> stringList = new ArrayList<>();
        stringList.addAll(strings);
        System.out.println(stringList);

        System.out.println("add----------");

        List<Object> stringList1 = new ArrayList<>();
        stringList1.add(11);
        stringList1.add(strings);
        System.out.println(stringList1);
    }


    public static Stream<Character> getCharacterStream(String str) {
        List<Character> list = new ArrayList<>();
        for (Character c : str.toCharArray()) {
            list.add(c);
        }
        return list.stream();
    }

}
