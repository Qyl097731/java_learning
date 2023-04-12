package com.nju.concurrent.ch05.demo02;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

/**
 * @description
 * @date:2022/12/19 20:27
 * @author: qyl
 */
public interface Computable<A,V>{
    V compute(A arg) throws InterruptedException, ExecutionException;
}

