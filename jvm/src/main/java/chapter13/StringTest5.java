package chapter13;

/**
 * @description
 * @date:2022/8/5 22:54
 * @author: qyl
 */
public class StringTest5 {
    public static void main(String[] args) {
        String s1 = "a"+"b";
        String s2 = "ab";
        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2));

        String s3 = "javaEE";
        String s4 = "hadoop";

        String s5 = "javaEEhadoop";
        String s6 = "javaEE" + "hadoop";
        String s7 = s3 + "hadoop";
        String s8 = "javaEE" + s4;
        String s9 = s3 + s3;

        System.out.println(s5 == s6);
        System.out.println(s5 == s7);
        System.out.println(s5 == s8);
        System.out.println(s5 == s9);
        System.out.println(s7 == s8);
        System.out.println(s7 == s9);
        System.out.println(s8 == s9);
//        intern 如果没有见创建常量池 否则返回常量池的地址
        String s10 = s8.intern();
        System.out.println(s10 == s5);
    }
}
