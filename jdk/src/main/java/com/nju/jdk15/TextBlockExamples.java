package com.nju.jdk15;

/**
 * @description
 * @date 2024/6/12 0:21
 * @author: qyl
 */
public class TextBlockExamples {
    public void getCode(String type) {
        String code1 = """
              public void print($type o) {
                  System.out.println(Objects.toString(o));
              }
              """.replace("$type", type);
        System.out.println (code1);

        String code2 = String.format("""
              public void print(%s o) {
                  System.out.println(Objects.toString(o));
              }
              """, type);
        System.out.println (code2);

        String code3 = """
                public void print(%s object) {
                    System.out.println(Objects.toString(object));
                }
                """.formatted(type);
        System.out.println (code3);
    }
}
