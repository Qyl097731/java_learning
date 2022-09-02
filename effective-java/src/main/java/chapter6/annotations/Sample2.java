package chapter6.annotations;

/**
 * @author qyl
 * @program Sample2.java
 * @createTime 2022-09-02 14:13
 */
public class Sample2 {
    @ExceptionTest(ArithmeticException.class)
    public static void m1() {
        int i = 0;
        i = i / i;
    }

    @ExceptionTest(ArithmeticException.class)
    public static void m2() {
        int[] a = new int[0];
        int i = a[1];
    }

    @ExceptionTest(ArithmeticException.class)
    public static void m3() {
    }

    @ExceptionRTest(ArithmeticException.class)
    @ExceptionRTest(RuntimeException.class)
    public static void m4() {
    }
}
