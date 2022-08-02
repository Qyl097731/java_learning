package chapter06;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author qyl
 * @program AtomicReferenceDemo.java
 * @Description TODO
 * @createTime 2022-08-01 15:02
 */

public class AtomicReferenceDemo {
    public static void main(String[] args) {
        AtomicReference<User> atomicReference = new AtomicReference<>();
        User z3 = new User("z3",23);
        User li4 = new User("li4",28);

        atomicReference.set(z3);

        System.out.println(atomicReference.compareAndSet(z3, li4)+"\t"+atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(z3, li4)+"\t"+atomicReference.get().toString());

    }
}

@AllArgsConstructor
@Data
class User{
    String username;
    int age;
}
