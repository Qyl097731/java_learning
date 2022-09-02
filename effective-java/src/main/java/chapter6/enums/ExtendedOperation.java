package chapter6.enums;

/**
 * @author qyl
 * @program ExtendedOperation.java
 * @createTime 2022-09-02 13:21
 */
public enum ExtendedOperation implements Operation1 {
    EXP("^") {
        @Override
        public double apply(double x, double y) {
            return Math.pow(x, y);
        }
    },
    REMAINDER("%") {
        @Override
        public double apply(double x, double y) {
            return x % y;
        }
    };

    private final String symbol;

    ExtendedOperation(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
