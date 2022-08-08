package chapter01;

/**
 * @description
 * @date:2022/8/8 22:59
 * @author: qyl
 */
public class IntegerTest {
    public static void main(String[] args) {
        Integer x = 5;
        int y = 5;
        System.out.println(x == y);

        Integer i1 = 10, i2 = 10, i3 = 128, i4 = 128;
        System.out.println(i1 == i2);
        System.out.println(i3 == i4);
    }
}
