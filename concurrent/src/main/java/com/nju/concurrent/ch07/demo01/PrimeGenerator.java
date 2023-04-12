package com.nju.concurrent.ch07.demo01;

import net.jcip.annotations.GuardedBy;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 通过volatile保存状态
 * @date:2022/12/21 16:11
 * @author: qyl
 */
public class PrimeGenerator implements Runnable{

    @GuardedBy ("this")
    private final List<BigInteger> primes = new ArrayList<> (  );
    private volatile boolean cancelled;

    @Override
    public void run() {
        BigInteger p = BigInteger.ONE;
        while(!cancelled){
            p = p.nextProbablePrime ();
            synchronized (this){
                primes.add (p);
            }
        }
    }

    public void cancel(){cancelled = true;}

    public synchronized List<BigInteger> get(){return new ArrayList<> (primes);}
}
