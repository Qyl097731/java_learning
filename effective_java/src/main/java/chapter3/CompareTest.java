package chapter3;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @description
 * @date:2022/8/21 15:52
 * @author: qyl
 */
public class CompareTest {
    public static void main(String[] args) {
        Student qyl = new Student(12, "qyl");
        Student zhangsan = new Student(14, "zhangsan");
        List<Student> students = Arrays.asList(qyl, zhangsan);
        Collections.sort(students);
        System.out.println(students);
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Student implements Comparable<Student> {
    private int age;
    private String name;

    private static final Comparator<Student> COMPARATOR =
            Comparator.comparingInt(Student::getAge)
                    .thenComparing(Student::getName);

    @Override
    public int compareTo(Student student) {
        return -COMPARATOR.compare(this, student);
    }
}
