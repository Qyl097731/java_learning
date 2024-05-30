package com.nju.jdk10;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description
 * @date 2024/5/29 20:59
 * @author: qyl
 */
public class VarExamples {
    public static void main(String[] args) {
        // 传统方式
        String message1 = "Hello, World!";
        List<String> list1 = new ArrayList<> ();

        // 使用局部变量类型推断
        var message2 = "Hello, World!";
        var list2 = new ArrayList<String>();

        // 传统方式
        Map<String, Integer> map = new HashMap<> ();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key + " => " + value);
        }

        // 使用局部变量类型推断
        for (var entry : map.entrySet()) {
            var key = entry.getKey();
            var value = entry.getValue();
            System.out.println(key + " => " + value);
        }

        // 传统方式
        try (InputStream is = new FileInputStream ("file.txt");
             InputStreamReader isr = new InputStreamReader(is);
             BufferedReader reader = new BufferedReader(isr)) {
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 使用局部变量类型推断
        try (var is = new FileInputStream("file.txt");
             var isr = new InputStreamReader(is);
             var reader = new BufferedReader(isr)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
