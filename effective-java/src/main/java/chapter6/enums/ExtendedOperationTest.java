package chapter6.enums;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author qyl
 * @program ExtendedOperationTest.java
 * @createTime 2022-09-02 13:24
 */
public class ExtendedOperationTest {
    public static void main(String[] args) {
        double x = Double.parseDouble(args[0]);
        double y = Double.parseDouble(args[1]);
        test(ExtendedOperation.class, x, y);
        test(Arrays.asList(ExtendedOperation.values()), x, y);
    }

    private static <T extends Enum<T> & Operation1> void test(Class<T> opEnumType, double x, double y) {
        for (Operation1 op : opEnumType.getEnumConstants()) {
            System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
        }
    }

    private static void test(Collection<? extends Operation1> opSet, double x, double y) {
        for (Operation1 op : opSet) {
            System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
        }
    }
}
