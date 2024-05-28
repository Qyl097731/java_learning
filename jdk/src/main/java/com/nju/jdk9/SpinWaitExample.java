package com.nju.jdk9;

/**
 * @description
 * @date 2024/5/28 23:18
 * @author: qyl
 */
public class SpinWaitExample {
    private volatile boolean condition = false;

    public void waitForCondition() {
        while(!condition) {
            Thread.onSpinWait ();
        }
        System.out.println ("Condition change to true");
    }

    public void setCondition() {
        condition = true;
    }

    public static void main(String[] args) throws InterruptedException {
        SpinWaitExample example = new SpinWaitExample ();
        Thread waiter = new Thread (example::waitForCondition);
        waiter.start ();

        Thread.sleep (1000);

        example.setCondition ();
        waiter.join ();
    }
}
