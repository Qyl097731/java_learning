package chapter13;

/**
 * @description
 * @date:2022/8/5 23:35
 * @author: qyl
 */
public class StringIntern1 {
    public static void main(String[] args) {
//        String s1 = new String("1") + new String("2");
//        s1.intern();
//        String s2 = "12";
//        System.out.println(s1 == s2);

        String s3 = new String("1") + new String("2");
        String s4 = "12";
        String s5;
        s3.intern();
        System.out.println(s3 == s4);
        System.out.println((s5 = s3.intern()) == s4);

    }
}
