package chapter01;

/**
 * @description
 * @date:2022/8/8 23:02
 * @author: qyl
 */
public class StringTest {
    public static void main(String[] args) {
        String str = new String("a") + new String("b");
        String str1 = "ab";
        System.out.println(str == str1);
    }
}
