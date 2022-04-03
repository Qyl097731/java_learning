package java8.day01.selectEmployee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * projectName:  java_learing
 * packageName: Java8
 * date: 2022-04-02 08:51
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class SelectObjects {
    static List<Employee> list = new ArrayList<>(Arrays.asList(
            new Employee(31, 301, Employee.Status.FREE),
            new Employee(32, 302, Employee.Status.BUSY),
            new Employee(59, 306, Employee.Status.FREE),
            new Employee(99, 301, Employee.Status.VOCATION),
            new Employee(99, 301, Employee.Status.FREE),
            new Employee(33, 303, Employee.Status.VOCATION)
    ));
    public static List<Employee> filterEmployee(List<Employee>list,MyFilter<Employee>mp){
        List<Employee>resultList = new ArrayList<>();

        for(Employee e:list){
            if(mp.check(e)){
                resultList.add(e);
            }
        }

        return resultList;
    }

}
