package com.nju.concurrent.ch15;

import net.jcip.annotations.ThreadSafe;

/**
 * @description 使用CAS实现的非阻塞计数器
 * @date:2023/1/9 20:51
 * @author: qyl
 */
@ThreadSafe
public class CasCounter {
    private SimulatedCAS value;

    public int getValue(){
        return value.get ();
    }

    public int increment(){
        int v;
        do {
            v = value.get ();
        }while (v != value.compareAndSwap (v,v+1));
        return v+1;
    }
}
