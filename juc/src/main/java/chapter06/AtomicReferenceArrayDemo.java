package chapter06;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * @author qyl
 * @program AtomicReferenceArrayDemo.java
 * @Description TODO
 * @createTime 2022-08-01 16:09
 */

public class AtomicReferenceArrayDemo {
    public static User[] users = new User[]{new User(1),new User(2),new User(3)};
    public static void main(String[] args) {
        AtomicReferenceArray<User> userAtomicReferenceArray = new AtomicReferenceArray<User>(users);
        System.out.println(userAtomicReferenceArray.compareAndSet(1,users[1],users[0]));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static
    class User{
        private Integer age;
    }
}
