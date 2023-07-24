package grammer;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * list测试
 * @author qiuyiliang
 */
public class ListTest {

    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        for (Object o : list) {
            System.out.println(1);
        }

        List<Student> students = Arrays.asList(new Student(1,"yy"),new Student(2,"ds"));
       if (CollectionUtils.isEmpty(students)){
           return;
       }
       students.forEach(s->{
           System.out.println(s.getAge());
       });
    }

    static class Student{
        private int age;
        private String name;

        public Student(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
