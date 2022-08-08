package chapter13;

/**
 * @description
 * @date:2022/8/5 23:40
 * @author: qyl
 */
public class StringIntern2 {
    public static void main(String[] args) {
        String s = new String("a") + new String("b");
        String s2 = s.intern();

        System.out.println(s2 == "ab");
        System.out.println(s == "ab");
    }
}
