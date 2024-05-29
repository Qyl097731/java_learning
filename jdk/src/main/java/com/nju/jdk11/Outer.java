package com.nju.jdk11;

/**
 * @description
 * @date 2024/5/29 17:49
 * @author: qyl
 */
public class Outer {
    private String outerField = "Outer Field";

    public class Inner {
        private String innerField = "Inner Field";

        public void accessOuterField() {
            System.out.println(outerField);  // 访问外部类的私有字段
        }
    }

    public void accessInnerField() {
        Inner inner = new Inner();
        System.out.println(inner.innerField);  // 访问内部类的私有字段
    }

}
