package com.nju.concurrent.ch04;

import net.jcip.annotations.NotThreadSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @description 出现了错误用锁，导致同步策略失效，在别的线程获取到了list的情况下，已经释放了锁，之后进行list的修改；同时又一个线程检查helper的锁，拿到之后就执行putIfAbsent，显然出错了。应该锁list
 * @date:2022/12/17 20:50
 * @author: qyl
 */
public class ListHelper<E> {
    public List<E> list = Collections.synchronizedList (new ArrayList<E> ( ));

    /**
     * 锁没用在UnsafeListHelper上
     * @param x
     * @return
     */
    public synchronized boolean putIfAbsent1(E x) {
        boolean absent = !list.contains (x);
        if (absent) list.add (x);
        return absent;
    }

    /**
     * 正确的锁行为
     */
    public boolean putIfAbsent2(E x){
        synchronized (list){
            boolean absent = !list.contains (x);
            if (absent) list.add (x);
            return absent;
        }
    }

}
