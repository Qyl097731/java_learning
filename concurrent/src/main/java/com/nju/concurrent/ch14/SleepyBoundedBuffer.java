package com.nju.concurrent.ch14;

/**
 * @description 有限缓存使用了拙劣的阻塞
 * @date:2023/1/3 17:37
 * @author: qyl
 */
public class SleepyBoundedBuffer<V> extends BaseBoundedBuffer<V>{
    protected SleepyBoundedBuffer(int capacity) {
        super (capacity);
    }

    public void put(V v) throws InterruptedException {
        while (true){
            synchronized (this){
                if (!isFull ()){
                    doPut (v);
                    return;
                }
            }
            Thread.sleep (1000);
        }
    }

    public V take() throws InterruptedException {
        while (true){
            synchronized (this){
                if (!isEmpty ()){
                    return doTake ();
                }
            }
            Thread.sleep (1000);
        }
    }
}
