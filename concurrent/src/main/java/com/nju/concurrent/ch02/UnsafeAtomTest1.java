package com.nju.concurrent.ch02;

import com.nju.concurrent.entity.User;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 虽然user age各自都是原子操作，但是组合起来并不能保证正确
 *
 * @date:2022/12/14 15:11
 * @author: qyl
 */
public class UnsafeAtomTest1 {
    static AtomicReference<User> user = new AtomicReference<> (new User ());
    static AtomicInteger age = new AtomicInteger ( );

    public static void main(String[] args) {
        new Thread (() -> {
            updateSafely ( );
//            updateUnsafely();
        }, "t1").start ();
        new Thread (() -> {
            updateSafely ( );
//            updateUnsafely();
        }, "t2").start ();
        new Thread (() -> {
            updateSafely ( );
//            updateUnsafely();
        }, "t3").start ();
    }

    static void updateUnsafely() {
        update();
    }

    static synchronized void updateSafely(){
        update();
    }

    static void update(){
        age.getAndAdd (1);
        user.getAndUpdate (u -> {
            u.age = age.get ( );
            return u;
        });
        System.out.println (Thread.currentThread ( ).getName ( ) + " " + user.get ( ).age);
    }
}
