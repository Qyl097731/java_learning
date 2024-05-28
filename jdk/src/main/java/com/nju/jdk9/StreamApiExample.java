package com.nju.jdk9;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @description
 * @date 2024/5/28 23:30
 * @author: qyl
 */
public class StreamApiExample {
    public static void main(String[] args) {
        testForTakeWhile();
        testForDropWhile();
        testForOfNullable();
        testForIterate();
    }

    //  takeWhile用于从 Stream 中获取一部分数据，接收一个 Predicate 来选择从开头开始的满足条件的元素。
    public static void testForTakeWhile(){
        List<Integer> list = Arrays.asList(10,20,30,40,30,20,10);
        list.stream().takeWhile(t->t<40).forEach(t -> System.out.print(t + " "));
        System.out.println ();

        List<Integer> list2 = Arrays.asList(1,2,3,4,5,6,7);
        list2.stream().takeWhile(t->t<7).forEach(t -> System.out.print(t + " "));
        System.out.println ();
    }

    // dropWhile 的行为与 takeWhile 相反，返回剩余的元素
    public static void testForDropWhile() {
        List<Integer> list = Arrays.asList(10,20,30,40,30,20,10);
        list.stream().dropWhile(t->t<40).forEach(t-> System.out.print(t + " "));
        System.out.println ();

        List<Integer> list2 = Arrays.asList(1,2,3,4,5,6,7);
        list2.stream().dropWhile(t->t<7).forEach(t -> System.out.print(t + " "));
        System.out.println ();
    }

    // ofNullable 方法允许我们创建一个Stream，可以所有元素均为空。
    public static void testForOfNullable() {
        // JDK8允许通过
        Stream<String> streams = Stream.of("AA","BB",null);
        System.out.println(streams.count());
        // JDK8不允许通过
        /*Stream<Object> stream2 = Stream.of(null);
        System.out.println(stream2.count());*/
        // JDK9允许通过
        Stream<Object> stream2 = Stream.ofNullable(null);
        System.out.println(stream2.count());
    }

    // iterate 可以让你提供一个 Predicate (判断条件)来指定什么时候结束迭代。
    public static void testForIterate() {
        // 原始方式
        Stream.iterate(1,i->i+1).limit(50).forEach(t -> System.out.print(t + " "));
        System.out.println ();

        // 增强方式
        Stream.iterate(1,i->i<60,i->i+1).forEach(t -> System.out.print(t + " "));
    }
}
