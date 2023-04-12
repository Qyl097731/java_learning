package com.nju.concurrent.ch03;

import org.junit.jupiter.api.Test;

/**
 * @description 构造函数尚未完成，就隐含地把对于自身的引用给了他人使用，出现了溢出。当前对象被认为”没有正确构建的“
 * @date:2022/12/16 18:34
 * @author: qyl
 */
public class EscapeTest {
    @Test
    public void test1() {
        new EscapeTestSon ("qyl");
    }

    @Test
    public void test2(){
        CustomizeListener listener = ThisEscape2.newInstance ( );
        listener.run ();
    }
}

class ThisEscape1 {
    private int val;

    public ThisEscape1() {
        System.out.println ("invoke father's constructor");
        // 构造还未完成就已经发布了this引用，导致val还未初始化就被CustomizeListener调用了打印val的方法。
        new CustomizeListener ( ) {
            @Override
            public void run() {
                doSomething ( );
            }
        }.run ( );

        // 构造函数中调用非 final 和 private的方法，导致溢出
        methodEscape ( );
        // private 来解决上述问题
        methodEscapeSolution1();
        methodEscapeSolution2();
        val = 10;
    }

    // 溢出1，val大概率是0，属于未完成初始化就this引用溢出了
    public void doSomething() {
        System.out.println (val);
    }

    // 溢出2. 方法可能被重写，那么可能this引用溢出，导致后面的方法调用有问题
    public void methodEscape() {
        System.out.println ("call father's method");
    }

    // 正常功能 通过private解决构造函数中调用方法出现this引用溢出
    private void methodEscapeSolution1(){
        System.out.println ("call father's method1");
    }
    // 正常功能 通过 final 解决构造函数中调用方法出现this引用溢出
    public final void methodEscapeSolution2(){
        System.out.println ("call father's method2");
    }
}

class EscapeTestSon extends ThisEscape1 {
    private final String name;

    public EscapeTestSon(String name) {
        this.name = name;
        System.out.println ("invoke son's constructor");
    }

    @Override
    public void methodEscape() {
        System.out.println ("son's name ----> " + name);
    }

}

class ThisEscape2{
    private int val;
    private final CustomizeListener listener;
    private ThisEscape2() {
        listener = new CustomizeListener (){
            @Override
            public void run() {
                System.out.println (val);
            }
        };
        this.val = 10;
    }

    public static CustomizeListener newInstance(){
        ThisEscape2 escape2 = new ThisEscape2 ( );
        return escape2.listener;
    }
}

class CustomizeListener implements Runnable {

    @Override
    public void run() {
        System.out.println ("hello");
    }
}
