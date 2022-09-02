package chapter6.enums;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author qyl
 * @program Operation.java
 * @Description 特定于常量方法的实现 apply
 * @createTime 2022-09-02 09:54
 */
public enum Operation {
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

    private static final Map<String, Operation> stringTuEnum =
            Stream.of(values()).collect(Collectors.toMap(Object::toString, e -> e));

    Operation(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "symbol='" + symbol + '\'' +
                '}';
    }

    public static Optional<Operation> fromString(String symbol){
        return Optional.ofNullable(stringTuEnum.get(symbol));
    }

    public abstract double apply(double x, double y);
}
