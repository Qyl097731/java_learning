package java8.day01.selectEmployee;

/**
 * projectName:  java_learing
 * packageName: SelectObjects
 * date: 2022-04-02 08:56
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class MyFilterByAge implements MyFilter<Employee> {
    @Override
    public boolean check(Employee employee) {
        return employee.getAge() > 32;
    }
}
