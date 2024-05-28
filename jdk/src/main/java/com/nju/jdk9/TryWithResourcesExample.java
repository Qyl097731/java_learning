package com.nju.jdk9;

/**
 * @description
 * @date 2024/5/20 19:18
 * @author: qyl
 */
import java.io.*;

public class TryWithResourcesExample {
    public static void main(String[] args) throws IOException {
        // 在 try 块外部声明和初始化资源
        Reader reader = new BufferedReader(new FileReader("test.txt"));
        try (reader) {
            // 使用资源
            System.out.println(reader.read());
        }
    }
}
