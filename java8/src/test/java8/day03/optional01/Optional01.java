package java8.day03.optional01;

import java8.day01.selectEmployee.Employee;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * projectName:  java_learing
 * packageName: java8.day03.optional01
 * date: 2022-04-04 16:26
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Optional01 {
    /**
     * Optional.of(T t) : 创建一个Optional实例
     * Optional.empty() : 创建一个空的Optional实例
     * Optional.ofNullable(T t) : 若t不是null 就创建Optional实列，否则创建空实例
     * isPresent() ： 判断是否包含值
     * orElse(T t)  : 如果调用对象包含值，返回该值，否则返回t
     * orElseGet(Supplier s) :如果对象包含值就返回值，否则返回s获取的值
     * map(Function f): 如果有值就进行处理，返回处理后的Optional，否则返回Optional.empty()
     * flatMap(Function mapper) : 与 map相似，返回值必须是Optional
     */
    @Test
    public void test01() {
        /**
         *
         * 空指针仍然是错误，但是他会让你更快的发现具体空指针在哪里出现
         *
         */
        Optional<Employee> op = Optional.of(null);
        Employee employee = op.get();
        System.out.println(employee);

    }

    @Test
    public void test02() {
        Optional<Employee> op = Optional.ofNullable(null);
//        System.out.println(op.get());
        Employee employee = op.orElseGet(Employee::new);
        System.out.println(employee);
    }

    @Test
    public void test03() {
        Optional<Employee> op = Optional.ofNullable(null);
        if (op.isPresent()) {
            System.out.println(op.get());
        }
        Employee employee = op.orElse(new Employee(19, 18, Employee.Status.FREE));
        System.out.println(employee);
    }

    @Test
    public void test04(){
        Optional<Employee> op = Optional.ofNullable(new Employee());

        Optional<Integer> integer = op.map(Employee::getSalary);
        System.out.println(integer.get());

        Optional<Integer> integer1 = op.flatMap(employee -> Optional.of(employee.getAge()));
        System.out.println(integer1.get());
    }
}
