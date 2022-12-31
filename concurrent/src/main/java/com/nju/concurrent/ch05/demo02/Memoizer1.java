package com.nju.concurrent.ch05.demo02;

import net.jcip.annotations.GuardedBy;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @description 通过HashMap和同步进行初始化和缓存
 * @date:2022/12/19 20:29
 * @author: qyl
 */
public class Memoizer1 <A,V> implements Computable<A,V>{
    @GuardedBy ("this")
    private final Map<A,V> cache = new HashMap<> ();
    private final Computable<A,V> c;

    public Memoizer1(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public synchronized V compute(A arg) throws InterruptedException, ExecutionException {
        V result = cache.get (arg);
        if (result == null){
            result = c.compute (arg);
            cache.put (arg,result);
        }
        return result;
    }
}
