package com.nju.concurrent.ch05.demo02;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

/**
 * @description
 * @date:2022/12/19 20:28
 * @author: qyl
 */
public class ExpensiveFunction implements Computable<String, BigInteger> {

    @Override
    public BigInteger compute(String arg) throws InterruptedException {
        TimeUnit.SECONDS.sleep (4);
        return new BigInteger (arg);
    }
}
