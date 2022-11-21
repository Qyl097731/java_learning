package com.nju.netty.ch07;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @description 定时任务
 * @date:2022/11/21 22:48
 * @author: qyl
 */
public class ScheduledServiceTest {
    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool (10);

    public static void main(String[] args) throws InterruptedException {
        ScheduledFuture<?> future = executor.schedule (()->{
            System.out.println ("5 secodes later" );
        },5, TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep (20);
        executor.shutdown ();
    }

}
