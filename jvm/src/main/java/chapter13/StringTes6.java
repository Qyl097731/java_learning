package chapter13;

/**
 * @description
 * @date:2022/8/5 23:10
 * @author: qyl
 */
public class StringTes6 {
    public static void main(String[] args) {

        System.out.println(method1(100000));
        System.out.println(method2(100000));

    }

    public static long method1(int len){
        String s = "";
        long start = System.currentTimeMillis();
        for (int i = 0; i < len; i++) {
            s += "a";
        }
        long end = System.currentTimeMillis();
        return - (start - end);
    }

    public static long method2(int len){
        StringBuilder s = new StringBuilder(100000);
        long start = System.currentTimeMillis();
        for (int i = 0; i < len; i++) {
            s.append("a");
        }
        long end = System.currentTimeMillis();
        return - (start - end);

    }
}
