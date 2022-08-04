package chapter06;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.LongBinaryOperator;

/**
 * @author qyl
 * @program LongAdderApiDemo.java
 * @Description TODO
 * @createTime 2022-08-01 16:56
 */
public class LongAdderApiDemo {

    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();

        longAdder.increment();
        longAdder.increment();
        longAdder.increment();

        System.out.println(longAdder.sum());

        LongAccumulator longAccumulator = new LongAccumulator((x,y)->x+y,0);
        longAccumulator.accumulate(1);
        longAccumulator.accumulate(2);
        System.out.println(longAccumulator.get());
    }
}
