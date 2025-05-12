package com.nju.jdk16;

/**
 * @description
 * @date 2024/6/26 0:17
 * @author: qyl
 */
public class PatternExample {
    public static void main(String[] args) {
        class Example1 {
            String s;

            void test1(Object o) {
                if (o instanceof String s) {
                    System.out.println (s);      // Field s is shadowed
                    s = s + "\n";               // Assignment to pattern variable
                } else {
                    System.out.println (s);          // Refers to field s
                }
            }
        }

        new Example1().test1("Hello");
        new Example1().test1(new Object());
    }
}
