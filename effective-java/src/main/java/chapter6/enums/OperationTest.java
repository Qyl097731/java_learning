package chapter6.enums;

/**
 * @author qyl
 * @program OperationTest.java
 * @createTime 2022-09-02 10:00
 */
public class OperationTest {
    public static void main(String[] args) {
        double x = Double.parseDouble(args[0]);
        double y = Double.parseDouble(args[1]);
        for (Operation op : Operation.values()) {
            System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
        }
    }
}
