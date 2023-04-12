package com.nju.concurrent.ch05.demo02;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

/**
 * @description 通过ConcurrentHashMap实现缓存
 * @date:2022/12/19 20:39
 * @author: qyl
 */
public class Memoizer2<A,V> implements Computable<A,V> {
    private final Map<A,V> cache = new ConcurrentHashMap<> (  );
    private final Computable<A,V> c;

    public Memoizer2(Computable<A, V> c) {
        this.c = c;
    }

    /**
     * 如果计算事件过长 可能导致阻塞在compute，而这个时候其他线程发现没有计算结果，会重复计算！！
     * @param arg
     * @return
     * @throws InterruptedException
     */
    @Override
    public V compute(A arg) throws InterruptedException, ExecutionException {
        V result = cache.get (arg);
        if (result == null){
            result = c.compute (arg);
            cache.put (arg,result);
        }
        return result;
    }
}
