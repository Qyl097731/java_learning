package com.nju.concurrent.ch05.demo02;

import java.util.concurrent.*;

/**
 * @description 高效的缓存 解决了重复计算、计算相同值、以及计算失败带来的坏处，但是不能把旧的计算移除，腾出新的控件。
 * @date:2022/12/19 21:03
 * @author: qyl
 */
public class Memoizer<A, V> implements Computable<A, V> {

    private final ConcurrentHashMap<A, Future<V>> cache = new ConcurrentHashMap<> ( );
    private final Computable<A, V> c;

    public Memoizer(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException, ExecutionException {
        while (true) {
            Future<V> f = cache.get (arg);
            if (f == null) {
                Callable<V> eval = () -> c.compute (arg);
                FutureTask<V> task = new FutureTask<> (eval);
                f = cache.putIfAbsent (arg, task);
                if (f == null) {
                    f = task;
                    task.run ( );
                }
            }
            try {
                return f.get ();
            }catch (CancellationException e){
                cache.remove (arg,f);
            }catch (ExecutionException e){
                e.printStackTrace ();
            }
        }
    }
}
