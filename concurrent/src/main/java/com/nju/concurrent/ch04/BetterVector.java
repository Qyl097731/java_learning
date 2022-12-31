package com.nju.concurrent.ch04;

import java.util.Vector;

/**
 * @date:2022/12/17 20:46
 * @author: qyl
 */
public class BetterVector<E> extends Vector<E> {
    public synchronized boolean putIfAbsent(E x) {
        boolean absent = !contains (x);
        if (absent) {
            add (x);
        }
        return absent;
    }
}
