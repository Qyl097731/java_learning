package com.nju.jdk14;

/**
 * @description
 * @date 2024/6/5 23:22
 * @author: qyl
 */
public class TextBlockExamples {
    public static void main(String[] args) {
        String colorsBefore = """
            red  
            green
            blue 
            """;

        String colorsAfter = """
            red  \s
            green\s
            blue \s
            """;

        System.out.println (colorsBefore);
        System.out.println (colorsAfter);

        String textBefore = """
                Lorem ipsum dolor sit amet, consectetur adipiscing 
                elit, sed do eiusmod tempor incididunt ut labore 
                et dolore magna aliqua.
                """;

        String textAfter = """
                Lorem ipsum dolor sit amet, consectetur adipiscing \
                elit, sed do eiusmod tempor incididunt ut labore \
                et dolore magna aliqua.\
                """;
        System.out.println (textBefore);
        System.out.println (textAfter);
    }
}
