package chapter5;

import java.util.function.UnaryOperator;

/**
 * @author qyl
 * @program GenericSingletonFactory.java
 * @Description 泛型单例
 * @createTime 2022-08-26 14:17
 */
public class GenericSingletonFactory {
    private static UnaryOperator<Object> IDENTITY_FN = (t) -> t;

    @SuppressWarnings("unchecked")
    public static <T> UnaryOperator<T> identityFunction() {
        return (UnaryOperator<T>) IDENTITY_FN;
    }

    public static void main(String[] args) {
        String[] strings = {"hello", "world"};
        UnaryOperator<String> sameString = identityFunction();
        for (String s : strings) {
            System.out.println(sameString.apply(s));
        }
        Number[] numbers = {1,2.0,3L};
        UnaryOperator<Number> sameNumber = identityFunction();
        for (Number n:numbers){
            System.out.println(sameNumber.apply(n));
        }
    }
}
