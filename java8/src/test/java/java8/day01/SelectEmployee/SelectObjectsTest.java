package java8.day01.SelectEmployee;


import org.junit.jupiter.api.Test;

import java.util.List;


/**
 * projectName:  java_learing
 * packageName: java8.day01.SelectEmployee
 * date: 2022-04-02 09:15
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class SelectObjectsTest {

    @Test
    public void test01() {
        System.out.println(SelectObjects.filterEmployee(SelectObjects.list, new MyFilterByAge()));
        System.out.println(SelectObjects.filterEmployee(SelectObjects.list, new MyFilterBySalary()));
    }

    @Test
    public void test02(){
        List<Employee>list = SelectObjects.filterEmployee(SelectObjects.list, new MyFilter<Employee>() {
            @Override
            public boolean check(Employee employee) {
                return employee.getSalary() < 302;
            }
        });
        System.out.println(list);
    }
    @Test
    public void test03(){
        List<Employee>list = SelectObjects.filterEmployee(SelectObjects.list,employee -> employee.getSalary() <= 302);
        list.forEach(System.out::println);
    }
    /*
    *stream api filter过滤两个职员并且输出
     */
    @Test
    public void test04(){
        SelectObjects.list.stream()
                .filter(employee -> employee.getSalary() < 302)
                .limit(2)
                .forEach(System.out::println);
    }
}
