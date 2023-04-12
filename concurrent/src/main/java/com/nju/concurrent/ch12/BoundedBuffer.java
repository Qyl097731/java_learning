package com.nju.concurrent.ch12;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @description 利用Semaphore实现有限缓存
 * @date:2023/1/1 23:13
 * @author: qyl
 */
@ThreadSafe
public class BoundedBuffer<E> {
    private final Semaphore availableItems,availableSpace;
    @GuardedBy ("this") private final E[] items;
    @GuardedBy ("this") private int putPosition = 0 ,takePosition = 0;


    public BoundedBuffer(int capacity) {
        availableItems = new Semaphore (0);
        availableSpace = new Semaphore (capacity);
        items = (E[]) new Object [capacity];
    }

    public boolean isEmpty(){
        return availableItems.availablePermits () == 0;
    }

    public boolean isFull(){
        return availableSpace.availablePermits () == 0;
    }

    public void put(E x) throws InterruptedException {
        availableSpace.acquire ();
        doInsert(x);
        availableItems.release ();
    }

    public E take() throws InterruptedException {
        availableItems.acquire ();
        E item = doExtract();
        availableSpace.release ();
        return item;
    }

    private synchronized void doInsert(E x){
        int i = putPosition;
        items[i] = x;
        putPosition = (++i == items.length) ? 0 : i;
    }

    private synchronized E doExtract(){
        int i = takePosition;
        E x = items[i];
        items[i] = null;
        takePosition = (++i == items.length) ? 0 : i;
        return x;
    }
}

class BoundedBufferTest{
    @Test
    void testIsEmptyWhenConstructed(){
        BoundedBuffer<Integer> bb = new BoundedBuffer<> (0);
        assertTrue (bb.isEmpty ());
        assertTrue (bb.isFull ());
    }
    @Test
    void testIsFullAfterPuts() throws InterruptedException {
        BoundedBuffer<Integer> bb = new BoundedBuffer<> (10);
        for (int i = 0; i < 10; i++) {
            bb.put (i);
        }
        assertTrue (bb.isFull ());
        assertFalse (bb.isEmpty ());
    }
    @Test
    void testTakeBlocksWhenEmpty(){
        final BoundedBuffer<Integer> bb = new BoundedBuffer<> (10);
        Thread taker = new Thread (() -> {
            try {
                int unused = bb.take ();
                fail (); // 运行到这里说明有错误
            } catch (InterruptedException e) {
            }
        });
        try {
            taker.start ();
            Thread.sleep (1000);
            taker.interrupt ();
            taker.join (1000);
            assertFalse (taker.isAlive ());
        } catch (InterruptedException e) {
            fail ();
        }
    }
}
