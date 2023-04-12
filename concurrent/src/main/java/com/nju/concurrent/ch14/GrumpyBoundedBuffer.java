package com.nju.concurrent.ch14;

/**
 * @description 如果有限缓存不满足先验条件，会停滞不前
 * @date:2023/1/3 17:22
 * @author: qyl
 */
public class GrumpyBoundedBuffer<V> extends BaseBoundedBuffer<V>{
    protected GrumpyBoundedBuffer(int capacity) {
        super (capacity);
    }

    public synchronized void put(V v) throws BufferFullException {
        if (isFull ()){
            throw new BufferFullException();
        }
        doPut (v);
    }

    public synchronized V take() throws BufferEmptyException {
        if (isEmpty ()){
            throw new BufferEmptyException();
        }
        return doTake();
    }
}
class BufferFullException extends Throwable {
    public BufferFullException() {
        System.out.println ("buf if full");
    }
}

class BufferEmptyException extends Throwable {
    public BufferEmptyException() {
        System.out.println ("buf is empty");
    }
}
