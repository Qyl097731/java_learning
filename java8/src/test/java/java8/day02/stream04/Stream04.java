package java8.day02.stream04;

import java8.day01.selectEmployee.Employee;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * projectName:  java_learing
 * packageName: java8.day02
 * date: 2022-04-03 20:08
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Stream04 {
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
        规约
        reduce(T identity , BinaryOperator) 可以将流中元素反复结合，得到一个值
    */
    @Test
    public void test01() {
       Integer sum = list.stream()
                .filter(Objects::nonNull)
                .map(Employee::getSalary)
                .reduce(0,Integer::sum);
        System.out.println(sum);
    }


    /*
        收集
        collect 将流转化为其他形式，接受一个Collector接口的实现，用于给Stream中元素汇总成想要的集合
     */

    @Test
    public void test02(){
        /*
            统计所有的年龄
         */
        List<Integer> ages = list.stream()
                .filter(Objects::nonNull)
                .map(Employee::getAge)
                .distinct()
                .collect(Collectors.toList());
        System.out.println("ages ::"+ages);
        System.out.println("----------------");

        //总数
        Long count = list.stream().filter(Objects::nonNull)
                .collect(Collectors.counting());
        System.out.println("count :: "+count);
        System.out.println("----------------");

        //平均值
        Double avg = list.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println("avg ::"+avg);
        System.out.println("----------------");

        /*
            收集
            collect将流转化为其他形式，接受一个Collector接口的实现，用于将stream汇总
        */
        DoubleSummaryStatistics sum = list.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println("总和 ::"+sum.getSum());
        System.out.println("平均值 ::"+sum.getAverage());
        System.out.println("最大值 ::"+sum.getMax());
        System.out.println("----------------");

    }


    @Test
    public void test03(){
        Map<Employee.Status,List<Employee>> statusListMap = list.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Employee::getStatus));
        System.out.println(statusListMap);
    }

    @Test
    public void test04(){
        Map<Employee.Status,Map<String,List<Employee>>> res = list.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Employee::getStatus,Collectors.groupingBy(employee -> {
                    if(employee.getAge() < 40){
                        return "青年";
                    }else{
                        return "中老年";
                    }
                })));
        System.out.println(res);
    }

    @Test
    public void test05(){
        Map<Boolean,List<Employee>>map = list.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.partitioningBy((employee)->employee.getAge()<40));
        System.out.println(map);
    }



}
