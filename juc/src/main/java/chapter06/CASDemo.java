package chapter06;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qyl
 * @program CASDemo.java
 * @Description TODO
 * @createTime 2022-08-01 14:39
 */
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        System.out.println(atomicInteger.compareAndSet(5, 2022) + "\t" + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 2022) + "\t" + atomicInteger.get());
    }
}
