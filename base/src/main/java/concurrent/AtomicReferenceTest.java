package concurrent;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @description
 * @date:2023/1/18 21:54
 * @author: qyl
 */
public class AtomicReferenceTest {
    public static void main(String[] args) {
        Person a = new Person ("a",11);
        AtomicReference<Person> ar = new AtomicReference<> (a);
        a.setAge (12);
        System.out.println (a.getAge () + " ar " + ar.get ().getAge ());

        Person b = new Person ("b", 12);
        ar.compareAndSet (a,b);
        System.out.println ("ar " + ar.get ());
    }
}
class Person{
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
