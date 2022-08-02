package chapter05;

/**
 * @description
 * @date:2022/7/31 17:27
 * @author: qyl
 */
public class JMMTest {
    public static void main(String[] args) {
        Dog dog = new Dog();
        new Thread(()->{
            System.out.println(dog.getAge());
        },"t1").start();

        new Thread(()->{
            dog.setAge();
        },"t2").start();
    }

}

class Dog {
    private volatile Integer age = 0;

    public int setAge() {
        return ++age;
    }

    public int getAge() {
        return age;
    }
}
