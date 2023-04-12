package com.nju.concurrent.ch15;

import net.jcip.annotations.Immutable;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @description 使用CAS避免多元的不变约束
 * @date:2023/1/11 14:40
 * @author: qyl
 */
public class CasNumberRange {
    @Immutable
    private static class IntPair{
        final int lower;
        final int upper;

        private IntPair(int lower, int upper) {
            this.lower = lower;
            this.upper = upper;
        }
    }

    private final AtomicReference<IntPair> values = new AtomicReference<> (new IntPair (0,0));

    public int getLower(){return values.get ().lower;}

    public int getUpper(){return values.get ().upper;}

    public void setLower(int i){
        while(true){
            IntPair oldv = values.get ();
            if (i > oldv.upper){
                throw new IllegalArgumentException ();
            }
            IntPair newv = new IntPair (i,oldv.upper);
            if (values.compareAndSet (oldv,newv)){
                return;
            }
        }
    }

    public void setUpper(int i){
        while(true){
            IntPair oldv = values.get ();
            if (i < oldv.lower){
                throw new IllegalArgumentException ();
            }
            IntPair newv = new IntPair (oldv.lower,i);
            if (values.compareAndSet (oldv,newv)){
                return;
            }
        }
    }


}
