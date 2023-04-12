package com.nju.concurrent.ch14;

/**
 * @description 有限缓存使用条件队列
 * @date:2023/1/3 17:59
 * @author: qyl
 */
public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {
    protected BoundedBuffer(int capacity) {
        super (capacity);
    }

    // 阻塞，直到 not-full
    public synchronized void put(V v) throws InterruptedException {
        while (isFull ()){
            wait ();
        }
        doPut (v);
        notifyAll ();
    }

    // 阻塞 直到 not-empty
    public synchronized V take() throws InterruptedException {
        while (isEmpty ()){
            wait ();
        }
        V v = doTake ();
        notifyAll ();
        return v;
    }
}
