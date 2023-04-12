package com.nju.concurrent.ch10;

/**
 * @description 简单的锁顺序死锁
 * @date:2022/12/28 18:12
 * @author: qyl
 */
public class LeftRightDeadLock {
    private final Object left = new Object ();
    private final Object right = new Object ();

    public void leftRight(){
        synchronized (left){
            synchronized (right){
                doSomething();
            }
        }
    }

    public void rightLeft(){
        synchronized (right){
            synchronized (left){
                doSomething ();
            }
        }
    }

    private void doSomething() {
    }
}
