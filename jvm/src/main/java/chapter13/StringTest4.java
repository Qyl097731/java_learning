package chapter13;

/**
 * @description 当存在字符串后 不会创建相同的
 * @date:2022/8/4 23:48
 * @author: qyl
 */
public class StringTest4 {
    public static void main(String[] args) {
        System.out.println();
        System.out.println("1");
        System.out.println("2");
        System.out.println("3");
        System.out.println("4");

        System.out.println("1");
        System.out.println("2");
        System.out.println("3");
        System.out.println("4");
    }
}
