package com.nju.concurrent.ch07.demo01;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description 让生成素数的程序运行一秒钟
 * @date:2022/12/21 16:14
 * @author: qyl
 */
public class PrimeTest {
    public static void main(String[] args) throws InterruptedException {
        aSecondOfPrimes ();
    }

    static List<BigInteger> aSecondOfPrimes() throws InterruptedException{
        PrimeGenerator generator = new PrimeGenerator ( );
        new Thread ( generator ).start ();

        try{
            TimeUnit.SECONDS.sleep (1);
        }finally {
            generator.cancel ();
        }
        return generator.get ();
    }
}
