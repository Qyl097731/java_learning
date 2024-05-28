package com.nju.jdk9;

/**
 * @description
 * @date 2024/5/23 21:41
 * @author: qyl
 */
import java.util.List;
import java.util.Set;
import java.util.Map;

public class CollectionsExample {
    public static void main(String[] args) {
        // 使用List.of()创建不可变的列表
        List<String> list = List.of("one", "two", "three");
        System.out.println("List: " + list); // 输出: List: [one, two, three]

        // 尝试修改列表会抛出UnsupportedOperationException
        // list.add("four"); // 这行代码会抛出异常

        // 使用Set.of()创建不可变的集合
        Set<String> set = Set.of("one", "two", "three");
        System.out.println("Set: " + set); // 输出: Set: [one, two, three]

        // 尝试修改集合会抛出UnsupportedOperationException
        // set.add("four"); // 这行代码会抛出异常

        // 使用Map.of()创建不可变的映射
        Map<String, Integer> map = Map.of("one", 1, "two", 2, "three", 3);
        System.out.println("Map: " + map); // 输出: Map: {one=1, two=2, three=3}

        // 使用Map.ofEntries()创建不可变的映射
        Map<String, Integer> mapEntries = Map.ofEntries(
                Map.entry("one", 1),
                Map.entry("two", 2),
                Map.entry("three", 3)
        );
        System.out.println("Map with Entries: " + mapEntries); // 输出: Map with Entries: {one=1, two=2, three=3}

        // 尝试修改映射会抛出UnsupportedOperationException
        // map.put("four", 4); // 这行代码会抛出异常
    }
}

