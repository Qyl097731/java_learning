package com.nju.jdk11;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

/**
 * @description
 * @date 2024/5/29 18:45
 * @author: qyl
 */
public class VarExample {
    public static void main(String[] args) {
        // 示例 1：基本用法
        List<String> list = Arrays.asList("one", "two", "three");

        // 使用传统方式声明 lambda 参数类型
        list.forEach((String s) -> System.out.println(s));

        // 使用 var 声明 lambda 参数类型
        list.forEach((var s) -> System.out.println(s));

        // 示例 2：使用多个参数
        BiFunction<Integer, Integer, Integer> add = (var x, var y) -> x + y;
        System.out.println("Sum: " + add.apply(5, 3)); // 输出: Sum: 8

        // 示例 3：结合注解使用
        // 使用注解和 var 关键字
        list.forEach((@Deprecated var s) -> System.out.println(s));
    }
}
