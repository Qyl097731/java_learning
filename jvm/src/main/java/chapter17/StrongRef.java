package chapter17;

import java.util.concurrent.TimeUnit;

/**
 * @description 强引用
 * @date:2022/8/7 10:24
 * @author: qyl
 */
public class StrongRef {
    public static void main(String[] args) throws InterruptedException {
        Stu stu = new Stu();
        System.out.println(stu);
        System.gc();
        TimeUnit.SECONDS.sleep(3);
        System.out.println(stu);

    }
}
class Stu{
    Integer age;
    String name;

    public Stu() {
        this.age = 1;
        this.name = "name";
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Stu{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
