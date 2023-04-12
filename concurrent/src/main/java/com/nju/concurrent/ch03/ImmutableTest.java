package com.nju.concurrent.ch03;

import net.jcip.annotations.Immutable;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * @description 通过不可变容器进行缓存
 * @date:2022/12/17 15:23
 * @author: qyl
 */
public class ImmutableTest {
    private volatile OneValueCache cache = new OneValueCache (BigInteger.valueOf (1),
            new BigInteger[]{BigInteger.valueOf (1)});

    @Test
    public void test() throws InterruptedException {
        Thread t1 = new Thread (() -> {
            while (cache.getFactors (BigInteger.valueOf (4)) == null) {
                System.out.println ("cache not hit");
            }
            try {
                TimeUnit.SECONDS.sleep (1);
            } catch (InterruptedException e) {
                throw new RuntimeException (e);
            }
            // 打印外部修改之后的数据
            System.out.println ("内部结果: " + Arrays.toString (cache.getFactors (BigInteger.valueOf (4))));
        });
        t1.start ();
        cache = new OneValueCache (BigInteger.valueOf (4), new BigInteger[]{BigInteger.valueOf (1), BigInteger.valueOf (2),
                BigInteger.valueOf (4)});
        BigInteger[] factors = cache.getFactors (BigInteger.valueOf (4));
        System.out.println ("缓存中的结果：" + Arrays.toString (factors));

        System.out.println ("外部进行修改： ");

        Arrays.fill (factors, BigInteger.valueOf (1));
        System.out.println ("外部结果：" + Arrays.toString (factors));
        // 阻塞线程的结束
        t1.join ();
    }
}

@Immutable
class OneValueCache {
    private final BigInteger lastNumber;
    private final BigInteger[] factors;

    OneValueCache(BigInteger lastNumber, BigInteger[] factors) {
        this.lastNumber = lastNumber;
        this.factors = Arrays.copyOf (factors, factors.length);
    }

    public BigInteger[] getFactors(BigInteger i) {
        if (lastNumber == null || !lastNumber.equals (i)) {
            return null;
        } else {
            return Arrays.copyOf (factors, factors.length);
        }
    }
}
