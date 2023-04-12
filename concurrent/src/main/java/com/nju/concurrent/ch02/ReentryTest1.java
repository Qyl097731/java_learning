package com.nju.concurrent.ch02;

/**
 * @description JVM将记录 锁的占有者 以及将请求计数
 * @date:2022/12/14 16:54
 * @author: qyl
 */
public class ReentryTest1 {
    public static void main(String[] args) {
        new LoggingWeight ().doSomething ();
    }
}

class Weight{
    public synchronized void doSomething(){
        System.out.println ("father call....");
    }
}
class LoggingWeight extends Weight{
    @Override
    public synchronized void doSomething() {
        System.out.println (this.getClass ().getSimpleName () + " call ...." );
        super.doSomething ( );
    }
}
