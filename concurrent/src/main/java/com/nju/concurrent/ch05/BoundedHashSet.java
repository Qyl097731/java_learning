package com.nju.concurrent.ch05;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * @description 用信号量来约束容器
 * @date:2022/12/19 19:35
 * @author: qyl
 */
public class BoundedHashSet<T> {
    private final Set<T> set;
    private final Semaphore sem;

    public BoundedHashSet(int bound) {
        this.set = Collections.synchronizedSet (new HashSet<> ( ));
        sem = new Semaphore (bound);
    }

    public boolean add(T o) throws InterruptedException {
        sem.acquire ( );
        boolean wasAdded = false;
        try {
            wasAdded = set.add (o);
            return wasAdded;
        } finally {
            if (!wasAdded) {
                sem.release ( );
            }
        }
    }

    public boolean remove(T o) {
        boolean wasRemoved = false;
        try {
            wasRemoved = set.remove (o);
        }finally {
            if (wasRemoved){
                sem.release ();
            }
        }
        return wasRemoved;
    }
}
