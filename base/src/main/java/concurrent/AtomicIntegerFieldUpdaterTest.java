package concurrent;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @description
 * @date:2023/1/18 22:25
 * @author: qyl
 */
public class AtomicIntegerFieldUpdaterTest {
    public static void main(String[] args) {
        AtomicIntegerFieldUpdater<User> a = AtomicIntegerFieldUpdater.newUpdater (User.class,"age");

        User user = new User ("a",22);
        System.out.println (a.getAndIncrement (user));
        System.out.println (user.getAge ());
        System.out.println (a.get (user));
    }
}
class User{
    private String name;
    // 必须是volatile
    public volatile int age;

    public User(String name, int age) {
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
