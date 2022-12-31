package com.nju.concurrent.ch05.demo02;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @description 通过Future.get 来解决重复计算问题，这里是缓存计算开始这一个任务
 * @date:2022/12/19 20:51
 * @author: qyl
 */
public class Memoizer3<A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<> ( );
    private final Computable<A, V> c;

    public Memoizer3(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException, ExecutionException {
        Future<V> future = cache.get (arg);
        if (future == null) {
            // 这里可能会 存在两个线程同时计算相同的值
            Callable<V> eval = () -> c.compute (arg);
            FutureTask<V> task = new FutureTask<> (eval);
            future = task;
            // 缓存计算任务
            cache.put (arg,task);
            // 开始计算
            task.run ();
        }
        return future.get ();
    }
}
