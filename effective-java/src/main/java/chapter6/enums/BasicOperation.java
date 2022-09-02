package chapter6.enums;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author qyl
 * @program BasicOperation.java
 * @createTime 2022-09-02 12:16
 */
public enum BasicOperation implements Operation1 {
    PLUS("+") {
        @Override
        public double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS("-") {
        @Override
        public double apply(double x, double y) {
            return x - y;
        }
    },
    TIMES("*") {
        @Override
        public double apply(double x, double y) {
            return x * y;
        }
    },
    DIVIDE("/") {
        @Override
        public double apply(double x, double y) {
            return x / y;
        }
    };

    private final String symbol;

    private static final Map<String, BasicOperation> stringTuEnum =
            Stream.of(values()).collect(Collectors.toMap(Object::toString, e -> e));

    BasicOperation(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "symbol='" + symbol + '\'' +
                '}';
    }

    public static Optional<BasicOperation> fromString(String symbol){
        return Optional.ofNullable(stringTuEnum.get(symbol));
    }
}
