package com.nju.concurrent.ch06;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @description Timer的混乱行为
 * @date:2022/12/21 10:46
 * @author: qyl
 */
public class OutOfTime {
    public static void main(String[] args) throws InterruptedException {
        Timer timer = new Timer ( );
        timer.schedule (new ThrowTask ( ), 1);
        TimeUnit.SECONDS.sleep (1);
        timer.schedule (new ThrowTask ( ), 1);
        TimeUnit.SECONDS.sleep (5);
    }

    static class ThrowTask extends TimerTask {

        @Override
        public void run() {
            throw new RuntimeException ( );
        }
    }
}
