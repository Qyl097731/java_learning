package trails;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.collections4.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class DuplicatedKeyTest {

    @Test
    public void test() {
        List<Student> students = Arrays.asList(new Student(1,1, Arrays.asList(new Address("Asia"), new Address("Bsia"))),
                new Student(1,1, Arrays.asList(new Address("China"))));
        Map<Integer, Student> studentMap = students.stream().collect(toMap(Student::getClassNo, stu -> stu));
    }

    @Test
    public void testForNew(){
        List<Student> students = Arrays.asList(new Student(1,1, Arrays.asList(new Address("Asia"), new Address("Bsia"))),
                new Student(1,2, Arrays.asList(new Address("China"))));
        Map<Integer, Map<Integer, List<Student>>> collect = students.stream().collect(Collectors.groupingBy(Student::getRoomNo, Collectors.groupingBy(Student::getClassNo)));
        Map<Integer, List<Student>> integerListMap = collect.get(1);
        if (MapUtils.isEmpty(integerListMap)){
            return;
        }
        Student student = integerListMap.get(null)
                .stream().findFirst()
                .orElse(null);
    }
    @AllArgsConstructor
    @Data
    class Student {
        private Integer roomNo;
        private Integer classNo;
        private List<Address> address;
    }

    @AllArgsConstructor
    @Data
    class Address {
        private String street;
    }

}