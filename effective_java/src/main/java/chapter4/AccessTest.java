package chapter4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @description
 * @date:2022/8/21 16:36
 * @author: qyl
 */
public class AccessTest {
    public static void main(String[] args) {
        Integer[] values = AccessClass.VALUES;
        values[0] = 4;
        System.out.println(Arrays.toString(AccessClass.VALUES));

        /**
         * 数组直接clone会返回一个新的数组空间 而不是原始数组的引用地址 外部修改不会影响原始数据
         */
        values = AccessClass.values();
        values[0] = 4;
        System.out.println(Arrays.toString(AccessClass.values()));
        System.out.println(Arrays.equals(values, AccessClass.values()));

        try {
            /**
             * 一个对象中还有引用类型的时候 ，返回的是引用类型的引用地址，外部修改会影响原始数据
             */
            Student student = AccessClass.getStudent();
            System.out.println(student);
            // 修改同一个引用类型的数组地址
            Integer[] hobbies = student.getHobbies();
            hobbies[0] = 4;
            System.out.println(AccessClass.getStudent());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        List<Integer> list = AccessClass.VALUES_LIST;
        list.set(0, 4);
        System.out.println(AccessClass.VALUES_LIST);
    }
}

class AccessClass {
    // 不安全
    public static final Integer[] VALUES = {1, 2, 3};

    private static final Integer[] VALUES1 = {1, 2, 3};
    /**
     * 安全1
     */
    public static final List<Integer> VALUES_LIST =
            Collections.unmodifiableList(Arrays.asList(VALUES1));

    /**
     * 安全2
     */
    public static Integer[] values() {
        return VALUES1.clone();
    }

    /**
     * clone 有危险
     */
    private static final Student STUDENT = new Student(new Integer[]{1, 2, 3});

    public static Student getStudent() throws CloneNotSupportedException {
        return (Student) STUDENT.clone();
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Student implements Cloneable {
    private Integer[] hobbies;

    /**
     * 如果存在引用类型的域 在clone的时候 返回的是引用地址 会引起意想不到的错误
     *
     * @return Object
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
